package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioNotFoundException;
import gov.justucuman.seguridad_barrial.domain.PropietarioUpdaterOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietarioUpdaterAdapter implements PropietarioUpdaterOutputPort {

    private final PropietarioRepository repository;
    private final PropietarioOutputAdapterMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Propietario findById(UUID id) {
        log.info("Buscando propietario para actualizar con id: {}", id);
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new PropietarioNotFoundException("Propietario no encontrado con id: " + id));
    }

    @Override
    @Transactional
    public void update(Propietario propietario) {
        log.info("Persistiendo actualizacion de propietario con id: {}", propietario.getId());
        repository.save(mapper.toEntity(propietario));
    }
}
