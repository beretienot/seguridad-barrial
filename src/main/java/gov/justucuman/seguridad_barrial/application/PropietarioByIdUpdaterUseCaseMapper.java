package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Apellido;
import gov.justucuman.seguridad_barrial.domain.Direccion;
import gov.justucuman.seguridad_barrial.domain.Dni;
import gov.justucuman.seguridad_barrial.domain.Email;
import gov.justucuman.seguridad_barrial.domain.Nombre;
import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.Telefono;

public class PropietarioByIdUpdaterUseCaseMapper {

    public Propietario applyUpdate(PropietarioByIdUpdaterCommand command, Propietario existing) {
        return existing.update(
            command.getNombre() != null ? new Nombre(command.getNombre()) : null,
            command.getApellido() != null ? new Apellido(command.getApellido()) : null,
            command.getDni() != null ? new Dni(command.getDni()) : null,
            command.getDireccion() != null ? new Direccion(command.getDireccion()) : null,
            command.getTelefono() != null ? new Telefono(command.getTelefono()) : null,
            command.getEmail() != null ? new Email(command.getEmail()) : null
        );
    }
}
