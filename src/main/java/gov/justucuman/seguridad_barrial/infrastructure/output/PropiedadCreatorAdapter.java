package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadCreatorOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioNotFoundException;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropiedadCreatorOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadRepository;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class PropiedadCreatorAdapter implements PropiedadCreatorOutputPort {

    private final PropiedadRepository propiedadRepository;
    private final PropietarioRepository propietarioRepository;
    private final PropiedadCreatorOutputAdapterMapper mapper;

    @Override
    @Transactional
    public void perform(Propiedad propiedad) {
        log.info("Persistiendo propiedad con id: {}", propiedad.getId());
        PropietarioEntity propietario = propietarioRepository
                .findById(propiedad.getPropietarioId().getValor())
                .orElseThrow(() -> new PropietarioNotFoundException(
                        "Propietario no encontrado con id: " + propiedad.getPropietarioId().getValor()));
        PropiedadEntity entity = mapper.toEntity(propiedad, propietario);
        propiedadRepository.save(entity);
        log.info("Propiedad persistida exitosamente con id: {}", propiedad.getId());
    }
}
