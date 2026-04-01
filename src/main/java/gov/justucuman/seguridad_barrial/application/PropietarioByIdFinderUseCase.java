package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioFinderOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietarioFinderUseCase implements PropietarioFinder {

    private final PropietarioFinderOutputPort outputPort;
    private final PropietarioFinderUseCaseMapper mapper;

    @Override
    public PropietarioFinderResult perform(UUID id) {
        log.info("Buscando propietario con id: {}", id);
        Propietario propietario = outputPort.findById(id);
        log.info("Propietario encontrado con id: {}", id);
        return mapper.toResult(propietario);
    }
}
