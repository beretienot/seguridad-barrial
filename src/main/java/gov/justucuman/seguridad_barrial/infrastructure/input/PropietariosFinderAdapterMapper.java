package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioByIdFinderResult;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PropietariosFinderAdapterMapper {

    List<PropietarioByIdFinderResponse> toResponse(List<PropietarioByIdFinderResult> results);
}
