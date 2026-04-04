package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public class EventoSeguridad {

    private final UUID id;
    private final PropiedadId propiedadId;
    private final TipoEventoSeguridad tipo;
    private final DescripcionEventoSeguridad descripcion;
    private final FechaEventoSeguridad fecha;

    public EventoSeguridad(UUID id,
                           PropiedadId propiedadId,
                           TipoEventoSeguridad tipo,
                           DescripcionEventoSeguridad descripcion,
                           FechaEventoSeguridad fecha) {
        if (id == null) {
            throw new IllegalArgumentException("El id es obligatorio");
        }
        if (propiedadId == null) {
            throw new IllegalArgumentException("El propiedadId es obligatorio");
        }
        if (tipo == null) {
            throw new IllegalArgumentException("El tipo es obligatorio");
        }
        if (descripcion == null) {
            throw new IllegalArgumentException("La descripcion es obligatoria");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("La fecha es obligatoria");
        }
        this.id = id;
        this.propiedadId = propiedadId;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public UUID getId() {
        return id;
    }

    public PropiedadId getPropiedadId() {
        return propiedadId;
    }

    public TipoEventoSeguridad getTipo() {
        return tipo;
    }

    public DescripcionEventoSeguridad getDescripcion() {
        return descripcion;
    }

    public FechaEventoSeguridad getFecha() {
        return fecha;
    }
}
