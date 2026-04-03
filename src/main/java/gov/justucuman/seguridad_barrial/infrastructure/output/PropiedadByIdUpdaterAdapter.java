package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadByIdUpdaterOutputPort;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class PropiedadByIdUpdaterAdapter implements PropiedadByIdUpdaterOutputPort {

    private final PropiedadRepository propiedadRepository;
    private final PropietarioRepository propietarioRepository;
    private final PropiedadCreatorOutputAdapterMapper mapper;

    @Override
    @Transactional
    public void perform(Propiedad propiedad) {
        log.info("Actualizando propiedad con id: {}", propiedad.getId());
        PropietarioEntity propietario = propietarioRepository
                .findById(propiedad.getPropietarioId().getValor())
                .orElseThrow(() -> new PropietarioNotFoundException(
                        "Propietario no encontrado con id: " + propiedad.getPropietarioId().getValor()));
        PropiedadEntity entity = mapper.toEntity(propiedad, propietario);
        propiedadRepository.save(entity);
        log.info("Propiedad actualizada exitosamente con id: {}", propiedad.getId());
    }
}
