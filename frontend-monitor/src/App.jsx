import { useEffect, useMemo, useRef, useState } from "react";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

function formatDateTime(value) {
  if (!value) return "-";
  const d = new Date(value);
  if (Number.isNaN(d.getTime())) return value;
  return d.toLocaleString("es-AR", {
    day: "2-digit",
    month: "2-digit",
    year: "numeric",
    hour: "2-digit",
    minute: "2-digit",
    second: "2-digit",
  });
}

function toNumberOrNull(value) {
  const parsed = Number(value);
  return Number.isFinite(parsed) ? parsed : null;
}

function buildOsmEmbedUrl(lat, lon) {
  const delta = 0.008;
  const minLon = lon - delta;
  const minLat = lat - delta;
  const maxLon = lon + delta;
  const maxLat = lat + delta;

  const bbox = `${minLon}%2C${minLat}%2C${maxLon}%2C${maxLat}`;
  return `https://www.openstreetmap.org/export/embed.html?bbox=${bbox}&layer=mapnik&marker=${lat}%2C${lon}`;
}

export default function App() {
  const [events, setEvents] = useState([]);
  const [status, setStatus] = useState("connecting");
  const [soundEnabled, setSoundEnabled] = useState(false);
  const audioContextRef = useRef(null);
  const soundEnabledRef = useRef(false);

  useEffect(() => {
    soundEnabledRef.current = soundEnabled;
  }, [soundEnabled]);

  const soundByType = {
    ROBO: [
      { frequency: 740, duration: 0.12, delay: 0.0, gain: 0.17, type: "sawtooth" },
      { frequency: 620, duration: 0.12, delay: 0.14, gain: 0.16, type: "sawtooth" },
      { frequency: 520, duration: 0.13, delay: 0.29, gain: 0.15, type: "sawtooth" },
    ],
    ALARMA: [
      { frequency: 990, duration: 0.1, delay: 0.0, gain: 0.2, type: "square" },
      { frequency: 660, duration: 0.1, delay: 0.12, gain: 0.2, type: "square" },
      { frequency: 990, duration: 0.1, delay: 0.24, gain: 0.2, type: "square" },
      { frequency: 660, duration: 0.1, delay: 0.36, gain: 0.2, type: "square" },
    ],
    VANDALISMO: [
      { frequency: 450, duration: 0.16, delay: 0.0, gain: 0.16, type: "triangle" },
      { frequency: 390, duration: 0.16, delay: 0.18, gain: 0.16, type: "triangle" },
    ],
    SOSPECHOSO: [
      { frequency: 520, duration: 0.1, delay: 0.0, gain: 0.14, type: "sine" },
      { frequency: 680, duration: 0.1, delay: 0.12, gain: 0.14, type: "sine" },
      { frequency: 520, duration: 0.1, delay: 0.24, gain: 0.14, type: "sine" },
    ],
    OTRO: [{ frequency: 880, duration: 0.2, delay: 0.0, gain: 0.16, type: "triangle" }],
    DEFAULT: [{ frequency: 880, duration: 0.2, delay: 0.0, gain: 0.16, type: "triangle" }],
  };

  const playNotificationSound = async (eventType = "DEFAULT") => {
    try {
      const AudioCtx = window.AudioContext || window.webkitAudioContext;
      if (!AudioCtx) return;

      if (!audioContextRef.current) {
        audioContextRef.current = new AudioCtx();
      }

      const context = audioContextRef.current;
      if (context.state === "suspended") {
        await context.resume();
      }

      const normalizedType = String(eventType || "").trim().toUpperCase();
      const pattern = soundByType[normalizedType] || soundByType.DEFAULT;

      pattern.forEach((tone) => {
        const oscillator = context.createOscillator();
        const gainNode = context.createGain();

        oscillator.type = tone.type;
        oscillator.frequency.value = tone.frequency;

        const startAt = context.currentTime + tone.delay;
        const endAt = startAt + tone.duration;

        gainNode.gain.setValueAtTime(0.0001, startAt);
        gainNode.gain.exponentialRampToValueAtTime(tone.gain, startAt + 0.02);
        gainNode.gain.exponentialRampToValueAtTime(0.0001, endAt);

        oscillator.connect(gainNode);
        gainNode.connect(context.destination);

        oscillator.start(startAt);
        oscillator.stop(endAt + 0.01);
      });
    } catch {
      // Ignore audio errors to avoid breaking real-time feed updates.
    }
  };

  const handleToggleSound = async () => {
    if (!soundEnabled) {
      setSoundEnabled(true);
      await playNotificationSound("DEFAULT");
      return;
    }

    setSoundEnabled(false);
  };

  useEffect(() => {
    const source = new EventSource(`${API_BASE_URL}/api/eventos-seguridad/stream`);

    source.addEventListener("open", () => {
      setStatus("connected");
    });

    source.addEventListener("evento-seguridad", (evt) => {
      let eventType = "DEFAULT";
      try {
        const payload = JSON.parse(evt.data);
        eventType = payload?.tipo;
        setEvents((prev) => [payload, ...prev].slice(0, 50));
      } catch {
        setEvents((prev) => [{ raw: evt.data, fecha: new Date().toISOString() }, ...prev].slice(0, 50));
      }

      if (soundEnabledRef.current) {
        playNotificationSound(eventType);
      }
    });

    source.onerror = () => {
      setStatus("disconnected");
    };

    return () => {
      source.close();
    };
  }, []);

  useEffect(() => {
    return () => {
      if (audioContextRef.current) {
        audioContextRef.current.close();
        audioContextRef.current = null;
      }
    };
  }, []);

  const statusLabel = useMemo(() => {
    if (status === "connected") return "Conectado";
    if (status === "disconnected") return "Desconectado";
    return "Conectando";
  }, [status]);

  const latestEventWithLocation = useMemo(
    () => events.find((event) => toNumberOrNull(event?.latitud) !== null && toNumberOrNull(event?.longitud) !== null),
    [events]
  );

  const latestLatitude = toNumberOrNull(latestEventWithLocation?.latitud);
  const latestLongitude = toNumberOrNull(latestEventWithLocation?.longitud);

  return (
    <main className="screen">
      <header className="hero">
        <p>Recepción en Tiempo Real</p>
        <h1>Monitor de Eventos de Seguridad</h1>
        <div className="hero-controls">
          <span className={`badge ${status}`}>{statusLabel}</span>
          <button type="button" className={`sound-toggle ${soundEnabled ? "on" : "off"}`} onClick={handleToggleSound}>
            {soundEnabled ? "Sonido activado" : "Activar sonido"}
          </button>
        </div>
      </header>

      <section className="feed">
        <article className="map-card">
          <h2>Ubicación del Último Evento</h2>
          {latestLatitude !== null && latestLongitude !== null ? (
            <>
              <iframe
                title="Mapa del evento"
                className="map-frame"
                loading="lazy"
                src={buildOsmEmbedUrl(latestLatitude, latestLongitude)}
              />
              <small>
                Lat: {latestLatitude.toFixed(6)} | Lon: {latestLongitude.toFixed(6)}
              </small>
            </>
          ) : (
            <p className="map-empty">Aún no hay coordenadas disponibles para mostrar en el mapa.</p>
          )}
        </article>

        {events.length === 0 ? (
          <article className="empty">Aún no se recibieron eventos. Esperando reportes...</article>
        ) : (
          events.map((event, index) => (
            <article className="item" key={`${event.id || "evt"}-${index}`}>
              <div className="row">
                <strong>{event.tipo || "EVENTO"}</strong>
                <time>{formatDateTime(event.fecha)}</time>
              </div>
              <p>{event.descripcion || event.raw}</p>
              <small>Propiedad: {event.propiedadId || "-"}</small>
              {(toNumberOrNull(event?.latitud) !== null && toNumberOrNull(event?.longitud) !== null) && (
                <small>
                  Coordenadas: {toNumberOrNull(event.latitud).toFixed(6)}, {toNumberOrNull(event.longitud).toFixed(6)}
                </small>
              )}
            </article>
          ))
        )}
      </section>
    </main>
  );
}
