package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.PropiedadByIdDeleterOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropiedadByIdDeleterAdapter implements PropiedadByIdDeleterOutputPort {

    private final PropiedadRepository repository;

    @Override
    @Transactional
    public void perform(UUID id) {
        log.info("Eliminando propiedad con id: {}", id);
        repository.deleteById(id);
        log.info("Propiedad eliminada exitosamente con id: {}", id);
    }
}
