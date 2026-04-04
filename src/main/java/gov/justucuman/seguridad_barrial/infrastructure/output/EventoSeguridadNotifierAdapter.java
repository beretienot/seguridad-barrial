package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.EventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridadNotifierOutputPort;
import gov.justucuman.seguridad_barrial.domain.Propiedad;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventoSeguridadNotifierAdapter implements EventoSeguridadNotifierOutputPort {

    private final EventoSeguridadStreamBroker streamBroker;

    @Override
    public void perform(EventoSeguridad eventoSeguridad, Propiedad propiedad) {
        streamBroker.notify(new EventoSeguridadStreamMessage(
                eventoSeguridad.getId(),
                eventoSeguridad.getPropiedadId().getValor(),
                eventoSeguridad.getTipo().getValor(),
                eventoSeguridad.getDescripcion().getValor(),
                eventoSeguridad.getFecha().getValor(),
                propiedad.getUbicacion() != null ? propiedad.getUbicacion().getLatitud() : null,
                propiedad.getUbicacion() != null ? propiedad.getUbicacion().getLongitud() : null
        ));
        log.info("Evento de seguridad notificado por stream con id: {}", eventoSeguridad.getId());
    }
}
