package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Apellido;
import gov.justucuman.seguridad_barrial.domain.Direccion;
import gov.justucuman.seguridad_barrial.domain.Dni;
import gov.justucuman.seguridad_barrial.domain.Email;
import gov.justucuman.seguridad_barrial.domain.Nombre;
import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.Telefono;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(imports = {Nombre.class, Apellido.class, Dni.class, Direccion.class, Telefono.class, Email.class})
public interface PropietarioCreatorUseCaseMapper {

    @Mapping(target = "nombre", expression = "java(new Nombre(command.getNombre()))")
    @Mapping(target = "apellido", expression = "java(new Apellido(command.getApellido()))")
    @Mapping(target = "dni", expression = "java(new Dni(command.getDni()))")
    @Mapping(target = "direccion", expression = "java(new Direccion(command.getDireccion()))")
    @Mapping(target = "telefono", expression = "java(new Telefono(command.getTelefono()))")
    @Mapping(target = "email", expression = "java(new Email(command.getEmail()))")
    Propietario toDomain(PropietarioCreatorCommand command);
}
