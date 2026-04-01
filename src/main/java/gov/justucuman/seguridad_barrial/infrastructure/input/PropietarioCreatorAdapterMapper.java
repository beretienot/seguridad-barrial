package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioCreatorCommand;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropietarioCreatorAdapterMapper {

    PropietarioCreatorCommand toCommand(PropietarioCreatorRequest request);
}
