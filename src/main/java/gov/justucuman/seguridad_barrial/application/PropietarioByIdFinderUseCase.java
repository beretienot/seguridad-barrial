package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdFinderOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class PropietarioByIdFinderUseCase implements PropietarioByIdFinder {

    private final PropietarioByIdFinderOutputPort outputPort;
    private final PropietarioByIdFinderUseCaseMapper mapper;

    @Override
    public PropietarioByIdFinderResult perform(UUID id) {
        log.info("Buscando propietario con id: {}", id);
        Propietario propietario = outputPort.findById(id);
        log.info("Propietario encontrado con id: {}", id);
        return mapper.toResult(propietario);
    }
}
