package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public interface PropietarioFinderOutputPort {
    Propietario findById(UUID id);
}
