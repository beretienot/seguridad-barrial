package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioCreatorOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioCreatorOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class PropietarioCreatorAdapter implements PropietarioCreatorOutputPort {

    private final PropietarioRepository repository;
    private final PropietarioCreatorOutputAdapterMapper mapper;

    @Override
    @Transactional
    public void perform(Propietario propietario) {
        log.info("Persistiendo propietario con id: {}", propietario.getId());
        PropietarioEntity entity = mapper.toEntity(propietario);
        repository.save(entity);
        log.info("Propietario persistido exitosamente con id: {}", propietario.getId());
    }
}
