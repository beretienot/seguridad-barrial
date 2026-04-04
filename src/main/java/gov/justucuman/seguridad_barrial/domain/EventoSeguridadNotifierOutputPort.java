package gov.justucuman.seguridad_barrial.domain;

public interface EventoSeguridadNotifierOutputPort {

    void perform(EventoSeguridad eventoSeguridad, Propiedad propiedad);
}
