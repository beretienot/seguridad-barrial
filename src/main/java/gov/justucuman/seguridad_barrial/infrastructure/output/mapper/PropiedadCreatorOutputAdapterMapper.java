package gov.justucuman.seguridad_barrial.infrastructure.output.mapper;

import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropiedadCreatorOutputAdapterMapper {

    @Mapping(target = "id", source = "propiedad.id")
    @Mapping(target = "propietario", source = "propietario")
    @Mapping(target = "direccion", expression = "java(propiedad.getDireccion().getValor())")
    @Mapping(target = "provincia", expression = "java(propiedad.getProvincia() != null ? propiedad.getProvincia().getValor() : null)")
    @Mapping(target = "localidad", expression = "java(propiedad.getLocalidad() != null ? propiedad.getLocalidad().getValor() : null)")
    @Mapping(target = "latitud", expression = "java(propiedad.getUbicacion() != null ? propiedad.getUbicacion().getLatitud() : null)")
    @Mapping(target = "longitud", expression = "java(propiedad.getUbicacion() != null ? propiedad.getUbicacion().getLongitud() : null)")
    PropiedadEntity toEntity(Propiedad propiedad, PropietarioEntity propietario);
}
