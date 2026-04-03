package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietariosFinderOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioByIdFinderOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietariosFinderAdapter implements PropietariosFinderOutputPort {

    private final PropietarioRepository repository;
    private final PropietarioByIdFinderOutputAdapterMapper mapper;

    @Override
    @Transactional(readOnly = true)
    public List<Propietario> perform() {
        log.info("Buscando todos los propietarios en base de datos");
        return repository.findAll()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
