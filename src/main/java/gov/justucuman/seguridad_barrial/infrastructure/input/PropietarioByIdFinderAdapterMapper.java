package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioByIdFinderResult;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropietarioByIdFinderAdapterMapper {
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "apellido", target = "apellido")
    @Mapping(source = "dni", target = "dni")
    @Mapping(source = "direccion", target = "direccion")
    @Mapping(source = "telefono", target = "telefono")
    @Mapping(source = "email", target = "email")
    PropietarioByIdFinderResponse toResponse(PropietarioByIdFinderResult result);
}
