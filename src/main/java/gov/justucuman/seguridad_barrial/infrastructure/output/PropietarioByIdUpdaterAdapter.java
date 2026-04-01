package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdUpdaterOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class PropietarioByIdUpdaterAdapter implements PropietarioByIdUpdaterOutputPort {

    private final PropietarioRepository repository;
    private final PropietarioOutputAdapterMapper mapper;

    @Override
    @Transactional
    public void perform(Propietario propietario) {
        log.info("Persistiendo actualizacion de propietario con id: {}", propietario.getId());
        repository.save(mapper.toEntity(propietario));
    }
}
