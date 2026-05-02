package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadCreatorOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdFinderOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PropiedadCreatorUseCase implements PropiedadCreator {

    private final PropietarioByIdFinderOutputPort propietarioFinderPort;
    private final PropiedadCreatorOutputPort outputPort;
    private final PropiedadCreatorUseCaseMapper mapper;

    @Override
    public void perform(PropiedadCreatorCommand command) {
        log.info("Iniciando creacion de propiedad con id: {}", command.getId());
        propietarioFinderPort.findById(command.getPropietarioId());
        Propiedad propiedad = mapper.toDomain(command);
        outputPort.perform(propiedad);
        log.info("Propiedad creada exitosamente con id: {}", command.getId());
    }
}
