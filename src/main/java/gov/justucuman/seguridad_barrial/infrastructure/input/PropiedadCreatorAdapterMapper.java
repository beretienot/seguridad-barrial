package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropiedadCreatorCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PropiedadCreatorAdapterMapper {

    @Mapping(target = "propietarioId", source = "propietarioId")
    PropiedadCreatorCommand toCommand(PropiedadCreatorRequest request, UUID propietarioId);
}
