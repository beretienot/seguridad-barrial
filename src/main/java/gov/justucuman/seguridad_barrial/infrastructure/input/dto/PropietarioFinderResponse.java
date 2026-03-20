package gov.justucuman.seguridad_barrial.infrastructure.input.dto;

import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
public class PropietarioFinderResponse {
    private UUID id;
    private String nombre;
    private String apellido;
    private String dni;
    private String direccion;
    private String telefono;
    private String email;
}
