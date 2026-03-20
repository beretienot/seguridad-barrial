package gov.justucuman.seguridad_barrial.infrastructure.input.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;

@Getter
@Setter
public class PropietarioUpdaterRequest {
    @NotBlank
    private String nombre;
    @NotBlank
    private String apellido;
    @NotBlank
    private String dni;
    @NotBlank
    private String direccion;
    private String telefono;
    @Email
    private String email;
}
