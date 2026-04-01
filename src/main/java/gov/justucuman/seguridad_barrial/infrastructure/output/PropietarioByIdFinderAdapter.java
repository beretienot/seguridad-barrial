package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioNotFoundException;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioByIdFinderOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietarioByIdFinderAdapter implements PropietarioByIdFinderOutputPort {

    private final PropietarioRepository repository;
    private final PropietarioByIdFinderOutputAdapterMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Propietario findById(UUID id) {
        log.info("Buscando propietario en base de datos con id: {}", id);
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new PropietarioNotFoundException("Propietario no encontrado con id: " + id));
    }
}
