package gov.justucuman.seguridad_barrial.infrastructure.input;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class PropiedadByIdFinderResponse {

    private UUID id;
    private UUID propietarioId;
    private String direccion;
    private String provincia;
    private String localidad;
    private BigDecimal latitud;
    private BigDecimal longitud;
}
