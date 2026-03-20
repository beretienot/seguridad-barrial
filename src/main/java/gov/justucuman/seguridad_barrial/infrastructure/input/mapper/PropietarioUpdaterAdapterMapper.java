package gov.justucuman.seguridad_barrial.infrastructure.input.mapper;

import gov.justucuman.seguridad_barrial.application.PropietarioUpdaterCommand;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioUpdaterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PropietarioUpdaterAdapterMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "request.nombre")
    @Mapping(target = "apellido", source = "request.apellido")
    @Mapping(target = "dni", source = "request.dni")
    @Mapping(target = "direccion", source = "request.direccion")
    @Mapping(target = "telefono", source = "request.telefono")
    @Mapping(target = "email", source = "request.email")
    PropietarioUpdaterCommand toCommand(UUID id, PropietarioUpdaterRequest request);
}
