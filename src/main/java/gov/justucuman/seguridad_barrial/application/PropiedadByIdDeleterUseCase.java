package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.PropiedadByIdDeleterOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropiedadByIdDeleterUseCase implements PropiedadByIdDeleter {

    private final PropiedadByIdDeleterOutputPort outputPort;

    @Override
    public void perform(UUID id) {
        log.info("Eliminando propiedad con id: {}", id);
        outputPort.perform(id);
        log.info("Propiedad eliminada exitosamente con id: {}", id);
    }
}
