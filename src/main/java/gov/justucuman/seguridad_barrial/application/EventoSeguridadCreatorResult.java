package gov.justucuman.seguridad_barrial.application;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class EventoSeguridadCreatorResult {

    private UUID id;
    private UUID propiedadId;
    private String tipo;
    private String descripcion;
    private LocalDateTime fecha;
    private BigDecimal latitud;
    private BigDecimal longitud;
}
