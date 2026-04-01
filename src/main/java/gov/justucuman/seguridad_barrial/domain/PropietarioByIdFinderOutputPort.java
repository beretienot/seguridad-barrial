package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public interface PropietarioByIdFinderOutputPort {
    Propietario findById(UUID id);
}
