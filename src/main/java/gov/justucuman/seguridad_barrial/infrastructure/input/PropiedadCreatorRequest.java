package gov.justucuman.seguridad_barrial.infrastructure.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadCreatorRequest {

    @NotNull
    private UUID id;

    @NotBlank
    private String direccion;

    private String provincia;

    private String localidad;

    private BigDecimal latitud;

    private BigDecimal longitud;
}
