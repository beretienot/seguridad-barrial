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
        @Mapping(target = "nombre", expression = "java(new gov.justucuman.seguridad_barrial.domain.Nombre(entity.getNombre()))"),
        @Mapping(target = "apellido", expression = "java(new gov.justucuman.seguridad_barrial.domain.Apellido(entity.getApellido()))"),
        @Mapping(target = "dni", expression = "java(new gov.justucuman.seguridad_barrial.domain.Dni(entity.getDni()))"),
        @Mapping(target = "direccion", expression = "java(new gov.justucuman.seguridad_barrial.domain.Direccion(entity.getDireccion()))"),
        @Mapping(target = "telefono", expression = "java(entity.getTelefono() != null ? new gov.justucuman.seguridad_barrial.domain.Telefono(entity.getTelefono()) : null)"),
        @Mapping(target = "email", expression = "java(entity.getEmail() != null ? new gov.justucuman.seguridad_barrial.domain.Email(entity.getEmail()) : null)")
    })
    Propietario toDomain(PropietarioEntity entity);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "nombre", source = "nombre.valor"),
        @Mapping(target = "apellido", source = "apellido.valor"),
        @Mapping(target = "dni", source = "dni.valor"),
        @Mapping(target = "direccion", source = "direccion.valor"),
        @Mapping(target = "telefono", source = "telefono.valor"),
        @Mapping(target = "email", source = "email.valor")
    })
    void updateEntityFromDomain(Propietario propietario, @MappingTarget PropietarioEntity entity);
}
