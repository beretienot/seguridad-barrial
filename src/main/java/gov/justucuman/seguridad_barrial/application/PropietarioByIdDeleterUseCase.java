package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.PropietarioRemoverOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietarioRemoverUseCase implements PropietarioRemover {

    private final PropietarioRemoverOutputPort outputPort;

    @Override
    public void perform(UUID id) {
        log.info("Eliminando propietario con id: {}", id);
        outputPort.deleteById(id);
        log.info("Propietario eliminado exitosamente con id: {}", id);
    }
}
