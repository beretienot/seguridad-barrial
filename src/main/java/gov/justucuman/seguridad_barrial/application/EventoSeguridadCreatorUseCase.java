package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.EventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridadCreatorOutputPort;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridadNotifierOutputPort;
import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadByIdFinderOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class EventoSeguridadCreatorUseCase implements EventoSeguridadCreator {

    private final PropiedadByIdFinderOutputPort propiedadFinderPort;
    private final EventoSeguridadCreatorOutputPort outputPort;
    private final EventoSeguridadNotifierOutputPort notifierOutputPort;
    private final EventoSeguridadCreatorUseCaseMapper mapper;

    @Override
    public EventoSeguridadCreatorResult perform(EventoSeguridadCreatorCommand command) {
        log.info("Iniciando registro de evento de seguridad con id: {}", command.getId());
        Propiedad propiedad = propiedadFinderPort.findById(command.getPropiedadId());
        EventoSeguridad evento = mapper.toDomain(command);
        outputPort.perform(evento);
        notifierOutputPort.perform(evento, propiedad);
        log.info("Evento de seguridad registrado exitosamente con id: {}", command.getId());
        return mapper.toResult(evento, propiedad);
    }
}
