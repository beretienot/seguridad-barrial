package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioListFinderOutputPort;
import gov.justucuman.seguridad_barrial.application.mapper.PropietarioFinderUseCaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropietarioListFinderUseCase implements PropietarioListFinder {

    private final PropietarioListFinderOutputPort outputPort;
    private final PropietarioFinderUseCaseMapper mapper;

    @Override
    public Page<PropietarioFinderResult> perform(Pageable pageable) {
        Page<Propietario> propietarios = outputPort.findAll(pageable);
        return propietarios.map(mapper::toResult);
    }
}
