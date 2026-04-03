package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Direccion;
import gov.justucuman.seguridad_barrial.domain.Localidad;
import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropietarioId;
import gov.justucuman.seguridad_barrial.domain.Provincia;
import gov.justucuman.seguridad_barrial.domain.UbicacionGps;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Direccion.class, PropietarioId.class, Provincia.class, Localidad.class, UbicacionGps.class})
public interface PropiedadCreatorUseCaseMapper {

    @Mapping(target = "propietarioId", expression = "java(new PropietarioId(command.getPropietarioId()))")
    @Mapping(target = "direccion", expression = "java(new Direccion(command.getDireccion()))")
    @Mapping(target = "provincia", expression = "java(command.getProvincia() != null ? new Provincia(command.getProvincia()) : null)")
    @Mapping(target = "localidad", expression = "java(command.getLocalidad() != null ? new Localidad(command.getLocalidad()) : null)")
    @Mapping(target = "ubicacion", expression = "java(command.getLatitud() != null && command.getLongitud() != null ? new UbicacionGps(command.getLatitud(), command.getLongitud()) : null)")
    Propiedad toDomain(PropiedadCreatorCommand command);
}
