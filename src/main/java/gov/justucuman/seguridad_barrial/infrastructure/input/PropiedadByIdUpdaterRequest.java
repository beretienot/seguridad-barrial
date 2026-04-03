package gov.justucuman.seguridad_barrial.infrastructure.input;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadByIdUpdaterRequest {

    @NotBlank
    private String direccion;

    private String provincia;

    private String localidad;

    private BigDecimal latitud;

    private BigDecimal longitud;

    @AssertTrue(message = "Latitud y longitud deben proporcionarse juntos o ninguno")
    private boolean isUbicacionValida() {
        return (latitud == null) == (longitud == null);
    }
}
