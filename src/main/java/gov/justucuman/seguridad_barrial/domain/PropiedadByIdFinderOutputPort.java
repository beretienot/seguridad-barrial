package gov.justucuman.seguridad_barrial.domain;

import java.util.UUID;

public interface PropiedadByIdFinderOutputPort {

    Propiedad findById(UUID id);
}
