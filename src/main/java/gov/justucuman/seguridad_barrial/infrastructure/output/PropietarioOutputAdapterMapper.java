package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PropietarioOutputAdapterMapper {
    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "nombre", source = "nombre.valor"),
        @Mapping(target = "apellido", source = "apellido.valor"),
        @Mapping(target = "dni", source = "dni.valor"),
        @Mapping(target = "direccion", source = "direccion.valor"),
        @Mapping(target = "telefono", source = "telefono.valor"),
        @Mapping(target = "email", source = "email.valor")
    })
    PropietarioEntity toEntity(Propietario propietario);

    @Mappings({
        @Mapping(target = "id", source = "id"),
        @Mapping(target = "nombre", expression = "java(new gov.justucuman.seguridad_barrial.domain.value.Nombre(entity.getNombre()))"),
        @Mapping(target = "apellido", expression = "java(new gov.justucuman.seguridad_barrial.domain.value.Apellido(entity.getApellido()))"),
        @Mapping(target = "dni", expression = "java(new gov.justucuman.seguridad_barrial.domain.value.Dni(entity.getDni()))"),
        @Mapping(target = "direccion", expression = "java(new gov.justucuman.seguridad_barrial.domain.value.Direccion(entity.getDireccion()))"),
        @Mapping(target = "telefono", expression = "java(entity.getTelefono() != null ? new gov.justucuman.seguridad_barrial.domain.value.Telefono(entity.getTelefono()) : null)"),
        @Mapping(target = "email", expression = "java(entity.getEmail() != null ? new gov.justucuman.seguridad_barrial.domain.value.Email(entity.getEmail()) : null)")
    })
    Propietario toDomain(PropietarioEntity entity);

    void updateEntityFromDomain(Propietario propietario, @MappingTarget PropietarioEntity entity);
}
