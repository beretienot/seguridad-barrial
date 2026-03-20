package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.PropietarioCreatorOutputPort;
import gov.justucuman.seguridad_barrial.domain.Propietario;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class PropietarioCreatorUseCase implements PropietarioCreator {

    private final PropietarioCreatorOutputPort outputPort;
    private final PropietarioCreatorUseCaseMapper mapper;

    @Override
    public void perform(PropietarioCreatorCommand command) {
        log.info("Iniciando creacion de propietario con id: {}", command.getId());
        Propietario propietario = mapper.toDomain(command);
        outputPort.perform(propietario);
        log.info("Propietario creado exitosamente con id: {}", command.getId());
    }
}
