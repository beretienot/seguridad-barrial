package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

import lombok.Getter;

@Getter
public class Propietario {

    private final UUID id;
    private final Nombre nombre;
    private final Apellido apellido;
    private final Dni dni;
    private final Direccion direccion;
    private final Telefono telefono;
    private final Email email;

    public Propietario(UUID id, Nombre nombre, Apellido apellido, Dni dni, Direccion direccion, Telefono telefono, Email email) {
        if (id == null) {
            throw new IllegalArgumentException("El id es obligatorio");
        }
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (apellido == null) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        if (dni == null) {
            throw new IllegalArgumentException("El DNI es obligatorio");
        }
        if (direccion == null) {
            throw new IllegalArgumentException("La direccion es obligatoria");
        }
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    public Propietario update(Nombre nombre, Apellido apellido, Dni dni, Direccion direccion, Telefono telefono, Email email) {
        return new Propietario(
            this.id,
            nombre != null ? nombre : this.nombre,
            apellido != null ? apellido : this.apellido,
            dni != null ? dni : this.dni,
            direccion != null ? direccion : this.direccion,
            telefono != null ? telefono : this.telefono,
            email != null ? email : this.email
        );
    }


}
