package gov.justucuman.seguridad_barrial.application;

import java.util.List;
import java.util.UUID;

public interface PropiedadByPropietarioIdFinder {

    List<PropiedadByIdFinderResult> perform(UUID propietarioId);
}
