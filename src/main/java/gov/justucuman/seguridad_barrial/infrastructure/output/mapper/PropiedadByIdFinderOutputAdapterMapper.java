package gov.justucuman.seguridad_barrial.infrastructure.output.mapper;

import gov.justucuman.seguridad_barrial.domain.Direccion;
import gov.justucuman.seguridad_barrial.domain.Localidad;
import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropietarioId;
import gov.justucuman.seguridad_barrial.domain.Provincia;
import gov.justucuman.seguridad_barrial.domain.UbicacionGps;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = {Direccion.class, PropietarioId.class, Provincia.class, Localidad.class, UbicacionGps.class})
public interface PropiedadByIdFinderOutputAdapterMapper {

    @Mapping(target = "propietarioId", expression = "java(new PropietarioId(entity.getPropietario().getId()))")
    @Mapping(target = "direccion", expression = "java(new Direccion(entity.getDireccion()))")
    @Mapping(target = "provincia", expression = "java(entity.getProvincia() != null ? new Provincia(entity.getProvincia()) : null)")
    @Mapping(target = "localidad", expression = "java(entity.getLocalidad() != null ? new Localidad(entity.getLocalidad()) : null)")
    @Mapping(target = "ubicacion", expression = "java(entity.getLatitud() != null && entity.getLongitud() != null ? new UbicacionGps(entity.getLatitud(), entity.getLongitud()) : null)")
    Propiedad toDomain(PropiedadEntity entity);
}
