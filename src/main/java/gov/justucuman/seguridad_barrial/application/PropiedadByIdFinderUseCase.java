package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadByIdFinderOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropiedadByIdFinderUseCase implements PropiedadByIdFinder {

    private final PropiedadByIdFinderOutputPort outputPort;
    private final PropiedadByIdFinderUseCaseMapper mapper;

    @Override
    public PropiedadByIdFinderResult perform(UUID id) {
        log.info("Buscando propiedad con id: {}", id);
        Propiedad propiedad = outputPort.findById(id);
        log.info("Propiedad encontrada con id: {}", id);
        return mapper.toResult(propiedad);
    }
}
