package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioListFinderOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioFinderOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietarioListFinderAdapter implements PropietarioListFinderOutputPort {

    private final PropietarioRepository repository;
    private final PropietarioFinderOutputAdapterMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Page<Propietario> findAll(Pageable pageable) {
        log.info("Buscando todos los propietarios paginados en base de datos");
        return repository.findAll(pageable).map(mapper::toDomain);
    }
}
