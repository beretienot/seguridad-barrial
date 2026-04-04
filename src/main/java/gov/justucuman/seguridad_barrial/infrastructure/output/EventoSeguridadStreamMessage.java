package gov.justucuman.seguridad_barrial.infrastructure.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record EventoSeguridadStreamMessage(
        UUID id,
        UUID propiedadId,
        String tipo,
        String descripcion,
        LocalDateTime fecha,
        BigDecimal latitud,
        BigDecimal longitud
) {
}
