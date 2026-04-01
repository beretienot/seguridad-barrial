package gov.justucuman.seguridad_barrial.infrastructure.output.mapper;

import gov.justucuman.seguridad_barrial.domain.Apellido;
import gov.justucuman.seguridad_barrial.domain.Direccion;
import gov.justucuman.seguridad_barrial.domain.Dni;
import gov.justucuman.seguridad_barrial.domain.Email;
import gov.justucuman.seguridad_barrial.domain.Nombre;
import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.Telefono;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Nombre.class, Apellido.class, Dni.class, Direccion.class, Telefono.class, Email.class})
public interface PropietarioByIdFinderOutputAdapterMapper {
    @Mapping(target = "nombre", expression = "java(new Nombre(entity.getNombre()))")
    @Mapping(target = "apellido", expression = "java(new Apellido(entity.getApellido()))")
    @Mapping(target = "dni", expression = "java(new Dni(entity.getDni()))")
    @Mapping(target = "direccion", expression = "java(new Direccion(entity.getDireccion()))")
    @Mapping(target = "telefono", expression = "java(entity.getTelefono() != null ? new Telefono(entity.getTelefono()) : null)")
    @Mapping(target = "email", expression = "java(entity.getEmail() != null ? new Email(entity.getEmail()) : null)")
    Propietario toDomain(PropietarioEntity entity);
}
