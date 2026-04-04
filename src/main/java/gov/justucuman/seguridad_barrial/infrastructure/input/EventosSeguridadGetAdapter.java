package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.infrastructure.output.EventoSeguridadStreamBroker;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Tag(name = "Eventos de Seguridad")
@Slf4j
@RestController
@RequestMapping("/api/eventos-seguridad")
@RequiredArgsConstructor
public class EventosSeguridadGetAdapter {

    private final EventoSeguridadStreamBroker streamBroker;

    @GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter perform() {
        log.info("Nueva suscripcion SSE para eventos de seguridad");
        return streamBroker.subscribe();
    }
}
