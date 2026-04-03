package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propiedad;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PropiedadByIdFinderUseCaseMapper {

    @Mapping(target = "propietarioId", expression = "java(propiedad.getPropietarioId().getValor())")
    @Mapping(target = "direccion", expression = "java(propiedad.getDireccion().getValor())")
    @Mapping(target = "provincia", expression = "java(propiedad.getProvincia() != null ? propiedad.getProvincia().getValor() : null)")
    @Mapping(target = "localidad", expression = "java(propiedad.getLocalidad() != null ? propiedad.getLocalidad().getValor() : null)")
    @Mapping(target = "latitud", expression = "java(propiedad.getUbicacion() != null ? propiedad.getUbicacion().getLatitud() : null)")
    @Mapping(target = "longitud", expression = "java(propiedad.getUbicacion() != null ? propiedad.getUbicacion().getLongitud() : null)")
    PropiedadByIdFinderResult toResult(Propiedad propiedad);
}
