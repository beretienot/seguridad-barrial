package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.PropietariosFinderOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietariosFinderUseCase implements PropietariosFinder {

    private final PropietariosFinderOutputPort outputPort;
    private final PropietarioByIdFinderUseCaseMapper mapper;

    @Override
    public List<PropietarioByIdFinderResult> perform() {
        log.info("Buscando todos los propietarios");
        List<PropietarioByIdFinderResult> results = outputPort.perform()
                .stream()
                .map(mapper::toResult)
                .toList();
        log.info("Se encontraron {} propietarios", results.size());
        return results;
    }
}
