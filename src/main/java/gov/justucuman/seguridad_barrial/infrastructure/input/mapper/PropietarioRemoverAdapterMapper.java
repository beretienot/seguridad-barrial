package gov.justucuman.seguridad_barrial.infrastructure.input.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropietarioRemoverAdapterMapper {
    // No mapping needed for delete, but interface required for constructor injection
}
