package gov.justucuman.seguridad_barrial.domain;

import java.util.List;
import java.util.UUID;

public interface PropiedadByPropietarioIdFinderOutputPort {

    List<Propiedad> findByPropietarioId(UUID propietarioId);
}
