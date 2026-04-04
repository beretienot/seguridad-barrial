package gov.justucuman.seguridad_barrial.infrastructure.input.dto;

import gov.justucuman.seguridad_barrial.infrastructure.input.EventoSeguridadCreatorRequest;

import java.util.UUID;

public class EventoSeguridadCreatorRequestMother {

    public static EventoSeguridadCreatorRequest valid() {
        return EventoSeguridadCreatorRequest.builder()
                .id(UUID.randomUUID())
                .tipo("ROBO")
                .descripcion("Se reporta intento de intrusion en el frente de la propiedad")
                .build();
    }

    public static EventoSeguridadCreatorRequest withoutTipo() {
        EventoSeguridadCreatorRequest request = valid();
        request.setTipo(null);
        return request;
    }

    public static EventoSeguridadCreatorRequest withoutId() {
        EventoSeguridadCreatorRequest request = valid();
        request.setId(null);
        return request;
    }
}
