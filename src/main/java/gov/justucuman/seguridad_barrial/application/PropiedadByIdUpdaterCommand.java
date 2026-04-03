package gov.justucuman.seguridad_barrial.application;

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
public class PropiedadByIdUpdaterCommand {

    private UUID id;
    private String direccion;
    private String provincia;
    private String localidad;
    private BigDecimal latitud;
    private BigDecimal longitud;
}
