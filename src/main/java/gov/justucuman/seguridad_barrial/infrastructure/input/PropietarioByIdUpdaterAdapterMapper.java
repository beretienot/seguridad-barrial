package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioByIdUpdaterCommand;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PropietarioByIdUpdaterAdapterMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", source = "request.nombre")
    @Mapping(target = "apellido", source = "request.apellido")
    @Mapping(target = "dni", source = "request.dni")
    @Mapping(target = "direccion", source = "request.direccion")
    @Mapping(target = "telefono", source = "request.telefono")
    @Mapping(target = "email", source = "request.email")
    PropietarioByIdUpdaterCommand toCommand(UUID id, PropietarioByIdUpdaterRequest request);
}
