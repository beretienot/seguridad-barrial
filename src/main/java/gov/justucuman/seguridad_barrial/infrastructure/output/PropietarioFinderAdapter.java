package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioFinderOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioFinderOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietarioFinderAdapter implements PropietarioFinderOutputPort {

    private final PropietarioRepository repository;
    private final PropietarioFinderOutputAdapterMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Propietario findById(UUID id) {
        log.info("Buscando propietario en base de datos con id: {}", id);
        PropietarioEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Propietario no encontrado"));
        return mapper.toDomain(entity);
    }
}
