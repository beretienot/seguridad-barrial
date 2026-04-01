package gov.justucuman.seguridad_barrial.application;

import lombok.Builder;
import lombok.Getter;
import java.util.UUID;

@Getter
@Builder
public class PropietarioByIdFinderResult {
    private UUID id;
    private String nombre;
    private String apellido;
    private String dni;
    private String direccion;
    private String telefono;
    private String email;
}
