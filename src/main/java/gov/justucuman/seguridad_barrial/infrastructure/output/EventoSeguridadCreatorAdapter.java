package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.EventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridadCreatorOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropiedadNotFoundException;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.EventoSeguridadCreatorOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.EventoSeguridadEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.EventoSeguridadRepository;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class EventoSeguridadCreatorAdapter implements EventoSeguridadCreatorOutputPort {

    private final EventoSeguridadRepository eventoSeguridadRepository;
    private final PropiedadRepository propiedadRepository;
    private final EventoSeguridadCreatorOutputAdapterMapper mapper;

    @Override
    @Transactional
    public void perform(EventoSeguridad eventoSeguridad) {
        log.info("Persistiendo evento de seguridad con id: {}", eventoSeguridad.getId());
        PropiedadEntity propiedad = propiedadRepository
                .findById(eventoSeguridad.getPropiedadId().getValor())
                .orElseThrow(() -> new PropiedadNotFoundException(
                        "Propiedad no encontrada con id: " + eventoSeguridad.getPropiedadId().getValor()));
        EventoSeguridadEntity entity = mapper.toEntity(eventoSeguridad, propiedad);
        eventoSeguridadRepository.save(entity);
        log.info("Evento de seguridad persistido exitosamente con id: {}", eventoSeguridad.getId());
    }
}
