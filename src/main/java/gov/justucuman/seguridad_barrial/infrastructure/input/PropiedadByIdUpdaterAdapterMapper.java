package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropiedadByIdUpdaterCommand;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PropiedadByIdUpdaterAdapterMapper {

    @Mapping(target = "id", source = "id")
    PropiedadByIdUpdaterCommand toCommand(PropiedadByIdUpdaterRequest request, UUID id);
}
