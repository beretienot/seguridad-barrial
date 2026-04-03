package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.PropiedadByPropietarioIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdFinderOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropiedadByPropietarioIdFinderUseCase implements PropiedadByPropietarioIdFinder {

    private final PropietarioByIdFinderOutputPort propietarioFinderPort;
    private final PropiedadByPropietarioIdFinderOutputPort outputPort;
    private final PropiedadByIdFinderUseCaseMapper mapper;

    @Override
    public List<PropiedadByIdFinderResult> perform(UUID propietarioId) {
        log.info("Buscando propiedades del propietario con id: {}", propietarioId);
        propietarioFinderPort.findById(propietarioId);
        List<PropiedadByIdFinderResult> results = outputPort.findByPropietarioId(propietarioId)
                .stream()
                .map(mapper::toResult)
                .toList();
        log.info("Se encontraron {} propiedades para el propietario con id: {}", results.size(), propietarioId);
        return results;
    }
}
