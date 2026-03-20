package gov.justucuman.seguridad_barrial.application;

import java.util.UUID;

public interface PropietarioFinder {
    PropietarioFinderResult perform(UUID id);
}
