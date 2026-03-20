package gov.justucuman.seguridad_barrial.infrastructure.input.mapper;

import gov.justucuman.seguridad_barrial.application.PropietarioCreatorCommand;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioCreatorRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropietarioCreatorAdapterMapper {

    PropietarioCreatorCommand toCommand(PropietarioCreatorRequest request);
}
