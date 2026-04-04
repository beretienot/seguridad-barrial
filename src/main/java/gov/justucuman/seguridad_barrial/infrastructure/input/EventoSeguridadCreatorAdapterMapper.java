package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.EventoSeguridadCreatorCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface EventoSeguridadCreatorAdapterMapper {

    @Mapping(target = "propiedadId", source = "propiedadId")
    EventoSeguridadCreatorCommand toCommand(EventoSeguridadCreatorRequest request, UUID propiedadId);
}
