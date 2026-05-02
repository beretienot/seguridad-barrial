import { useEffect, useMemo, useState } from "react";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

function generateUuid() {
  if (crypto && crypto.randomUUID) return crypto.randomUUID();
  return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function (c) {
    const r = (Math.random() * 16) | 0;
    const v = c === "x" ? r : (r & 0x3) | 0x8;
    return v.toString(16);
  });
}

function nowLocalDateTime() {
  const date = new Date();
  const pad = (value) => String(value).padStart(2, "0");
  return `${date.getFullYear()}-${pad(date.getMonth() + 1)}-${pad(date.getDate())}T${pad(date.getHours())}:${pad(date.getMinutes())}`;
}

export default function App() {
  const [propietarios, setPropietarios] = useState([]);
  const [propiedades, setPropiedades] = useState([]);
  const [form, setForm] = useState({
    propietarioId: "",
    propiedadId: "",
    tipo: "ROBO",
    descripcion: "",
    fecha: nowLocalDateTime(),
  });
  const [status, setStatus] = useState({ type: "idle", message: "" });
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    fetch(`${API_BASE_URL}/api/propietarios`)
      .then((res) => res.json())
      .then(setPropietarios)
      .catch(() => {});
  }, []);

  useEffect(() => {
    if (!form.propietarioId) {
      setPropiedades([]);
      setForm((prev) => ({ ...prev, propiedadId: "" }));
      return;
    }
    fetch(`${API_BASE_URL}/api/propietarios/${form.propietarioId}/propiedades`)
      .then((res) => res.json())
      .then(setPropiedades)
      .catch(() => {});
  }, [form.propietarioId]);

  const disabled = useMemo(
    () => loading || !form.propiedadId || !form.tipo || !form.descripcion.trim() || !form.fecha,
    [form, loading]
  );

  const handleChange = (field) => (e) => {
    setForm((prev) => ({ ...prev, [field]: e.target.value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);
    setStatus({ type: "idle", message: "" });

    try {
      const response = await fetch(
        `${API_BASE_URL}/api/propiedades/${form.propiedadId}/eventos-seguridad`,
        {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({
            id: generateUuid(),
            tipo: form.tipo,
            descripcion: form.descripcion,
            fecha: new Date(form.fecha).toISOString().slice(0, 19),
          }),
        }
      );

      if (!response.ok) {
        const body = await response.json().catch(() => ({}));
        throw new Error(body.error || `Error HTTP ${response.status}`);
      }

      setStatus({
        type: "ok",
        message: "Evento reportado. Los monitores conectados deberían recibir la notificación en tiempo real.",
      });
      setForm({ propietarioId: "", propiedadId: "", tipo: "ROBO", descripcion: "", fecha: nowLocalDateTime() });
    } catch (error) {
      setStatus({ type: "error", message: error.message || "No se pudo enviar el evento." });
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="page">
      <section className="panel">
        <p className="eyebrow">Centro de Operaciones</p>
        <h1>Reportar Evento de Seguridad</h1>
        <p className="lead">
          Registrá incidentes por propiedad y dispará una notificación en vivo al monitor de recepción.
        </p>

        <form onSubmit={handleSubmit} className="grid">
          <label>
            Propietario
            <select value={form.propietarioId} onChange={handleChange("propietarioId")}>
              <option value="">Seleccioná un propietario...</option>
              {propietarios.map((p) => (
                <option key={p.id} value={p.id}>
                  {p.apellido}, {p.nombre}
                </option>
              ))}
            </select>
          </label>

          <label>
            Propiedad
            <select
              value={form.propiedadId}
              onChange={handleChange("propiedadId")}
              disabled={!form.propietarioId || propiedades.length === 0}
            >
              <option value="">Seleccioná una propiedad...</option>
              {propiedades.map((p) => (
                <option key={p.id} value={p.id}>
                  {p.direccion} – {p.localidad}
                </option>
              ))}
            </select>
          </label>

          <label>
            Tipo
            <select value={form.tipo} onChange={handleChange("tipo")}>
              <option>ROBO</option>
              <option>ALARMA</option>
              <option>VANDALISMO</option>
              <option>SOSPECHOSO</option>
              <option>OTRO</option>
            </select>
          </label>

          <label>
            Fecha y hora
            <input type="datetime-local" value={form.fecha} onChange={handleChange("fecha")} />
          </label>

          <label className="full">
            Descripción
            <textarea
              rows={4}
              value={form.descripcion}
              onChange={handleChange("descripcion")}
              placeholder="Detalle breve del evento..."
            />
          </label>

          <button disabled={disabled} type="submit">
            {loading ? "Enviando..." : "Reportar Evento"}
          </button>
        </form>

        {status.type !== "idle" && (
          <p className={`status ${status.type === "ok" ? "ok" : "error"}`}>{status.message}</p>
        )}
      </section>
    </main>
  );
}
