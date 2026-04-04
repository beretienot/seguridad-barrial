package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadByIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropiedadByIdUpdaterOutputPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class PropiedadByIdUpdaterUseCase implements PropiedadByIdUpdater {

    private final PropiedadByIdFinderOutputPort finderPort;
    private final PropiedadByIdUpdaterOutputPort updaterPort;
    private final PropiedadByIdUpdaterUseCaseMapper mapper;

    @Override
    public void perform(PropiedadByIdUpdaterCommand command) {
        log.info("Iniciando actualizacion de propiedad con id: {}", command.getId());
        Propiedad existing = finderPort.findById(command.getId());
        Propiedad updated = mapper.applyUpdate(command, existing);
        updaterPort.perform(updated);
        log.info("Propiedad actualizada exitosamente con id: {}", command.getId());
    }
}
