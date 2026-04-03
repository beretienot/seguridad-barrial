package gov.justucuman.seguridad_barrial.application;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
public class PropiedadByIdFinderResult {

    private UUID id;
    private UUID propietarioId;
    private String direccion;
    private String provincia;
    private String localidad;
    private BigDecimal latitud;
    private BigDecimal longitud;
}
