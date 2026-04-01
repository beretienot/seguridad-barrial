package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioUpdaterOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietarioUpdaterUseCase implements PropietarioUpdater {

    private final PropietarioUpdaterOutputPort outputPort;
    private final PropietarioUpdaterUseCaseMapper mapper;

    @Override
    public void perform(PropietarioUpdaterCommand command) {
        log.info("Iniciando actualizacion de propietario con id: {}", command.getId());
        Propietario existing = outputPort.findById(command.getId());
        Propietario updated = mapper.applyUpdate(command, existing);
        outputPort.update(updated);
        log.info("Propietario actualizado exitosamente con id: {}", command.getId());
    }
}
