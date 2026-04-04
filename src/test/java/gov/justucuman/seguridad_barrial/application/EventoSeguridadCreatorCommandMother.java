package gov.justucuman.seguridad_barrial.application;

import java.util.UUID;

public class EventoSeguridadCreatorCommandMother {

    public static EventoSeguridadCreatorCommand valid() {
        return EventoSeguridadCreatorCommand.builder()
                .id(UUID.randomUUID())
                .propiedadId(UUID.randomUUID())
                .tipo("ALARMA")
                .descripcion("Se dispara alarma perimetral durante la madrugada")
                .build();
    }

    public static EventoSeguridadCreatorCommand withBlankTipo() {
        EventoSeguridadCreatorCommand command = valid();
        command.setTipo(" ");
        return command;
    }

    public static EventoSeguridadCreatorCommand withoutDescripcion() {
        EventoSeguridadCreatorCommand command = valid();
        command.setDescripcion(null);
        return command;
    }
}
