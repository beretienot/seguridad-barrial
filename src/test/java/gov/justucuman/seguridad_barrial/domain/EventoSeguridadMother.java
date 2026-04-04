package gov.justucuman.seguridad_barrial.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class EventoSeguridadMother {

    public static EventoSeguridad valid() {
        return new EventoSeguridad(
                UUID.randomUUID(),
                new PropiedadId(UUID.randomUUID()),
                new TipoEventoSeguridad("ROBO"),
                new DescripcionEventoSeguridad("Se detecta intento de ingreso por el patio trasero"),
                new FechaEventoSeguridad(LocalDateTime.now().minusMinutes(10).withNano(0))
        );
    }

    public static EventoSeguridad withPropiedadId(UUID propiedadId) {
        return new EventoSeguridad(
                UUID.randomUUID(),
                new PropiedadId(propiedadId),
                new TipoEventoSeguridad("VANDALISMO"),
                new DescripcionEventoSeguridad("Se informa dano en cerco perimetral"),
                new FechaEventoSeguridad(LocalDateTime.now().minusMinutes(30).withNano(0))
        );
    }
}
