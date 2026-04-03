package gov.justucuman.seguridad_barrial.application;

import java.util.UUID;

public interface PropiedadByIdFinder {

    PropiedadByIdFinderResult perform(UUID id);
}
