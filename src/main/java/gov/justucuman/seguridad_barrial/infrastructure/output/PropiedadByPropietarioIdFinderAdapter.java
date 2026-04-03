package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadByPropietarioIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropiedadByIdFinderOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropiedadByPropietarioIdFinderAdapter implements PropiedadByPropietarioIdFinderOutputPort {

    private final PropiedadRepository repository;
    private final PropiedadByIdFinderOutputAdapterMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Propiedad> findByPropietarioId(UUID propietarioId) {
        log.info("Buscando propiedades en base de datos para propietario con id: {}", propietarioId);
        return repository.findByPropietarioId(propietarioId)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
