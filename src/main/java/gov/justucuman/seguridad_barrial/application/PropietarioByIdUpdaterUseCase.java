package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdUpdaterOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietarioByIdUpdaterUseCase implements PropietarioByIdUpdater {

    private final PropietarioByIdFinderOutputPort finderPort;
    private final PropietarioByIdUpdaterOutputPort updaterPort;
    private final PropietarioByIdUpdaterUseCaseMapper mapper;

    @Override
    public void perform(PropietarioByIdUpdaterCommand command) {
        log.info("Iniciando actualizacion de propietario con id: {}", command.getId());
        Propietario existing = finderPort.findById(command.getId());
        Propietario updated = mapper.applyUpdate(command, existing);
        updaterPort.perform(updated);
        log.info("Propietario actualizado exitosamente con id: {}", command.getId());
    }
}
