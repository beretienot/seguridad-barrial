package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropiedadByIdFinderResult;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PropiedadByIdFinderAdapterMapper {

    PropiedadByIdFinderResponse toResponse(PropiedadByIdFinderResult result);
}
