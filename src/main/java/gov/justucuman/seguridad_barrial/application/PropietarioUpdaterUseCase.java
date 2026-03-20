package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioUpdaterOutputPort;
import gov.justucuman.seguridad_barrial.domain.Nombre;
import gov.justucuman.seguridad_barrial.domain.Apellido;
import gov.justucuman.seguridad_barrial.domain.Dni;
import gov.justucuman.seguridad_barrial.domain.Direccion;
import gov.justucuman.seguridad_barrial.domain.Telefono;
import gov.justucuman.seguridad_barrial.domain.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PropietarioUpdaterUseCase implements PropietarioUpdater {
    private final PropietarioUpdaterOutputPort outputPort;

    @Override
    public void perform(PropietarioUpdaterCommand command) {
        Propietario propietario = outputPort.findById(command.getId());
        Propietario updated = propietario.update(
            new Nombre(command.getNombre()),
            new Apellido(command.getApellido()),
            new Dni(command.getDni()),
            new Direccion(command.getDireccion()),
            command.getTelefono() != null ? new Telefono(command.getTelefono()) : null,
            command.getEmail() != null ? new Email(command.getEmail()) : null
        );
        outputPort.update(updated);
    }
}
