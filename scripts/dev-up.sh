#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
RUN_DIR="$ROOT_DIR/.run"
PID_FILE="$RUN_DIR/dev-up.pids"
mkdir -p "$RUN_DIR"

BACKEND_LOG="$RUN_DIR/backend.log"
REPORTER_LOG="$RUN_DIR/reporter.log"
MONITOR_LOG="$RUN_DIR/monitor.log"

BACKEND_PORT="${BACKEND_PORT:-8080}"
REPORTER_PORT="${REPORTER_PORT:-5173}"
MONITOR_PORT="${MONITOR_PORT:-5174}"
API_BASE_URL="${API_BASE_URL:-http://localhost:$BACKEND_PORT}"

DB_HOST="${DB_HOST:-localhost}"
DB_PORT="${DB_PORT:-5432}"
DB_NAME="${DB_NAME:-seguridad_barrial}"
DB_USER="${DB_USER:-postgres}"
DB_PASSWORD="${DB_PASSWORD:-clave}"

PROCESS_GROUPS=()

log() {
  printf '[dev-up] %s\n' "$1"
}

require_command() {
  local command_name="$1"

  if ! command -v "$command_name" >/dev/null 2>&1; then
    printf '[dev-up] Falta el comando requerido: %s\n' "$command_name" >&2
    exit 1
  fi
}

check_port_available() {
  local port="$1"
  local service_name="$2"

  if ss -ltn "( sport = :$port )" | grep -q ":$port"; then
    printf '[dev-up] El puerto %s ya esta en uso. Libera el puerto de %s o redefine la variable correspondiente.\n' "$port" "$service_name" >&2
    exit 1
  fi
}

prepare_frontend() {
  local frontend_dir="$1"
  local frontend_name="$2"

  if [[ ! -d "$frontend_dir/node_modules" ]]; then
    log "Instalando dependencias de $frontend_name"
    (
      cd "$frontend_dir"
      npm install
    )
  fi
}

ensure_database() {
  log "Verificando PostgreSQL en $DB_HOST:$DB_PORT"

  if ! PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d postgres -c 'select 1' >/dev/null 2>&1; then
    printf '[dev-up] No fue posible conectarse a PostgreSQL con %s@%s:%s.\n' "$DB_USER" "$DB_HOST" "$DB_PORT" >&2
    exit 1
  fi

  if ! PGPASSWORD="$DB_PASSWORD" psql -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" -d postgres -tAc "select 1 from pg_database where datname = '$DB_NAME'" | grep -q 1; then
    log "La base $DB_NAME no existe. Intentando crearla"
    PGPASSWORD="$DB_PASSWORD" createdb -h "$DB_HOST" -p "$DB_PORT" -U "$DB_USER" "$DB_NAME"
  fi
}

start_process() {
  local name="$1"
  local working_dir="$2"
  local log_file="$3"
  shift 3
  local -a command=("$@")
  local command_string=''
  local argument=''

  log "Iniciando $name"
  for argument in "${command[@]}"; do
    printf -v command_string '%s %q' "$command_string" "$argument"
  done

  setsid bash -lc "cd $(printf '%q' "$working_dir") && exec${command_string}" >"$log_file" 2>&1 &

  local pid=$!
  PROCESS_GROUPS+=("$pid")
  printf '%s:%s\n' "$name" "$pid" >> "$PID_FILE"
}

cleanup() {
  local exit_code="$1"
  local process_group=''

  if [[ ${#PROCESS_GROUPS[@]} -gt 0 ]]; then
    log "Deteniendo procesos"
    for process_group in "${PROCESS_GROUPS[@]}"; do
      kill -- "-$process_group" >/dev/null 2>&1 || true
    done
  fi

  rm -f "$PID_FILE"
  exit "$exit_code"
}

on_interrupt() {
  cleanup 0
}

trap on_interrupt INT TERM

require_command java
require_command node
require_command npm
require_command psql
require_command createdb
require_command ss

check_port_available "$BACKEND_PORT" 'backend'
check_port_available "$REPORTER_PORT" 'frontend-reporter'
check_port_available "$MONITOR_PORT" 'frontend-monitor'

ensure_database
prepare_frontend "$ROOT_DIR/frontend-reporter" 'frontend-reporter'
prepare_frontend "$ROOT_DIR/frontend-monitor" 'frontend-monitor'

: > "$PID_FILE"

start_process \
  'backend' \
  "$ROOT_DIR" \
  "$BACKEND_LOG" \
  env \
  SPRING_DATASOURCE_URL="jdbc:postgresql://$DB_HOST:$DB_PORT/$DB_NAME" \
  SPRING_DATASOURCE_USERNAME="$DB_USER" \
  SPRING_DATASOURCE_PASSWORD="$DB_PASSWORD" \
  SERVER_PORT="$BACKEND_PORT" \
  ./gradlew bootRun

start_process \
  'frontend-reporter' \
  "$ROOT_DIR/frontend-reporter" \
  "$REPORTER_LOG" \
  env \
  VITE_API_BASE_URL="$API_BASE_URL" \
  npm run dev -- --host 0.0.0.0 --port "$REPORTER_PORT"

start_process \
  'frontend-monitor' \
  "$ROOT_DIR/frontend-monitor" \
  "$MONITOR_LOG" \
  env \
  VITE_API_BASE_URL="$API_BASE_URL" \
  npm run dev -- --host 0.0.0.0 --port "$MONITOR_PORT"

cat <<EOF
[dev-up] Proyecto levantado.
[dev-up] Backend:  http://localhost:$BACKEND_PORT
[dev-up] Reporter: http://localhost:$REPORTER_PORT
[dev-up] Monitor:  http://localhost:$MONITOR_PORT
[dev-up] Swagger:  http://localhost:$BACKEND_PORT/swagger-ui.html
[dev-up] Logs:
[dev-up]   $BACKEND_LOG
[dev-up]   $REPORTER_LOG
[dev-up]   $MONITOR_LOG
[dev-up] Para detener todo, usa Ctrl+C o ejecuta scripts/dev-down.sh desde otra terminal.
EOF

set +e
wait -n "${PROCESS_GROUPS[@]}"
PROCESS_EXIT_CODE=$?
set -e

printf '[dev-up] Uno de los procesos termino con codigo %s. Revisa los logs.\n' "$PROCESS_EXIT_CODE" >&2
cleanup "$PROCESS_EXIT_CODE"