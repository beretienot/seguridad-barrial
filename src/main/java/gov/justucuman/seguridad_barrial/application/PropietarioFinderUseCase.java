package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioFinderOutputPort;
import gov.justucuman.seguridad_barrial.application.mapper.PropietarioFinderUseCaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PropietarioFinderUseCase implements PropietarioFinder {

    private final PropietarioFinderOutputPort outputPort;
    private final PropietarioFinderUseCaseMapper mapper;

    @Override
    public PropietarioFinderResult perform(UUID id) {
        Propietario propietario = outputPort.findById(id);
        return mapper.toResult(propietario);
    }
}
