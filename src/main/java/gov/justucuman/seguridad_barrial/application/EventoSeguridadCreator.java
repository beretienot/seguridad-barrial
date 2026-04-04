package gov.justucuman.seguridad_barrial.application;

public interface EventoSeguridadCreator {

    EventoSeguridadCreatorResult perform(EventoSeguridadCreatorCommand command);
}
