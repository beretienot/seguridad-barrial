package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public interface PropietarioRemoverOutputPort {
    void deleteById(UUID id);
}
