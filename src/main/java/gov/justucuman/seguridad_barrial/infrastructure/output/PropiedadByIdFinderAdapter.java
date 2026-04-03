package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadByIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropiedadNotFoundException;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropiedadByIdFinderOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropiedadByIdFinderAdapter implements PropiedadByIdFinderOutputPort {

    private final PropiedadRepository repository;
    private final PropiedadByIdFinderOutputAdapterMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public Propiedad findById(UUID id) {
        log.info("Buscando propiedad en base de datos con id: {}", id);
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new PropiedadNotFoundException("Propiedad no encontrada con id: " + id));
    }
}
