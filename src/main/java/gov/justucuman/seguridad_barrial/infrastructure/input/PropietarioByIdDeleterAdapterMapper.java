package gov.justucuman.seguridad_barrial.infrastructure.input;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropietarioByIdDeleterAdapterMapper {
    // No mapping needed for delete, but interface required for constructor injection
}
