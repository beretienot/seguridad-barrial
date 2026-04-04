package gov.justucuman.seguridad_barrial.infrastructure.output;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class EventoSeguridadStreamBroker {

    private static final long SSE_TIMEOUT_MS = 0L;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(SSE_TIMEOUT_MS);
        emitters.add(emitter);

        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError(error -> emitters.remove(emitter));

        return emitter;
    }

    public void notify(EventoSeguridadStreamMessage message) {
        List<SseEmitter> staleEmitters = new ArrayList<>();
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event()
                        .name("evento-seguridad")
                        .data(message, MediaType.APPLICATION_JSON));
            } catch (Exception ex) {
                log.warn("No se pudo enviar notificacion SSE: {}", ex.getMessage());
                staleEmitters.add(emitter);
            }
        }
        emitters.removeAll(staleEmitters);
    }
}
