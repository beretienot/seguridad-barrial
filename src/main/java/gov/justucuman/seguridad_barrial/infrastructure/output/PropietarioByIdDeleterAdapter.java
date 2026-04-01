package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.PropietarioByIdDeleterOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietarioByIdDeleterAdapter implements PropietarioByIdDeleterOutputPort {

    private final PropietarioRepository repository;

    @Override
    @Transactional
    public void perform(UUID id) {
        log.info("Eliminando propietario con id: {}", id);
        repository.deleteById(id);
    }
}
