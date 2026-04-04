#!/usr/bin/env bash

set -euo pipefail

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
PID_FILE="$ROOT_DIR/.run/dev-up.pids"

if [[ ! -f "$PID_FILE" ]]; then
  printf '[dev-down] No hay procesos registrados para detener.\n'
  exit 0
fi

while IFS=: read -r name pid; do
  if kill -0 -- "-$pid" >/dev/null 2>&1; then
    printf '[dev-down] Deteniendo %s (%s)\n' "$name" "$pid"
    kill -- "-$pid" >/dev/null 2>&1 || true
  fi
done < "$PID_FILE"

rm -f "$PID_FILE"
printf '[dev-down] Procesos detenidos.\n'