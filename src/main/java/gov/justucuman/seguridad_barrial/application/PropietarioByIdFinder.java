package gov.justucuman.seguridad_barrial.application;

import java.util.UUID;

public interface PropietarioByIdFinder {
    PropietarioByIdFinderResult perform(UUID id);
}
