package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PropietarioByIdFinderUseCaseMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "nombre", expression = "java(propietario.getNombre().getValor())")
    @Mapping(target = "apellido", expression = "java(propietario.getApellido().getValor())")
    @Mapping(target = "dni", expression = "java(propietario.getDni().getValor())")
    @Mapping(target = "direccion", expression = "java(propietario.getDireccion().getValor())")
    @Mapping(target = "telefono", expression = "java(propietario.getTelefono() != null ? propietario.getTelefono().getValor() : null)")
    @Mapping(target = "email", expression = "java(propietario.getEmail() != null ? propietario.getEmail().getValor() : null)")
    PropietarioByIdFinderResult toResult(Propietario propietario);
}
