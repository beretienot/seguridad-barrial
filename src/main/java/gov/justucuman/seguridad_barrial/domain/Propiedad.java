package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public class Propiedad {

    private final UUID id;
    private final PropietarioId propietarioId;
    private final Direccion direccion;
    private final Provincia provincia;
    private final Localidad localidad;
    private final UbicacionGps ubicacion;

    public Propiedad(UUID id, PropietarioId propietarioId, Direccion direccion,
                     Provincia provincia, Localidad localidad, UbicacionGps ubicacion) {
        if (id == null) {
            throw new IllegalArgumentException("El id es obligatorio");
        }
        if (propietarioId == null) {
            throw new IllegalArgumentException("El propietarioId es obligatorio");
        }
        if (direccion == null) {
            throw new IllegalArgumentException("La direccion es obligatoria");
        }
        this.id = id;
        this.propietarioId = propietarioId;
        this.direccion = direccion;
        this.provincia = provincia;
        this.localidad = localidad;
        this.ubicacion = ubicacion;
    }

    public Propiedad update(Direccion direccion, Provincia provincia, Localidad localidad, UbicacionGps ubicacion) {
        return new Propiedad(
            this.id,
            this.propietarioId,
            direccion != null ? direccion : this.direccion,
            provincia != null ? provincia : this.provincia,
            localidad != null ? localidad : this.localidad,
            ubicacion != null ? ubicacion : this.ubicacion
        );
    }

    public UUID getId() {
        return id;
    }

    public PropietarioId getPropietarioId() {
        return propietarioId;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public UbicacionGps getUbicacion() {
        return ubicacion;
    }
}
