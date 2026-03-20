package gov.justucuman.seguridad_barrial.infrastructure.output.mapper;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropietarioCreatorOutputAdapterMapper {

    @Mapping(source = "nombre.valor", target = "nombre")
    @Mapping(source = "apellido.valor", target = "apellido")
    @Mapping(source = "dni.valor", target = "dni")
    @Mapping(source = "direccion.valor", target = "direccion")
    @Mapping(source = "telefono.valor", target = "telefono")
    @Mapping(source = "email.valor", target = "email")
    PropietarioEntity toEntity(Propietario propietario);
}
