package gov.justucuman.seguridad_barrial.infrastructure.input.mapper;

import gov.justucuman.seguridad_barrial.application.PropietarioFinderResult;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioFinderResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropietarioFinderAdapterMapper {
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "apellido", target = "apellido")
    @Mapping(source = "dni", target = "dni")
    @Mapping(source = "direccion", target = "direccion")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "email", target = "email")
    PropietarioFinderResponse toResponse(PropietarioFinderResult result);
}
