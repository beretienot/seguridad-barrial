package gov.justucuman.seguridad_barrial.application;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class PropietarioByIdUpdaterCommand {
    private final UUID id;
    private final String nombre;
    private final String apellido;
    private final String dni;
    private final String direccion;
    private final String telefono;
    private final String email;
}
