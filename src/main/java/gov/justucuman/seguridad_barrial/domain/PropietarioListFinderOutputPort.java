package gov.justucuman.seguridad_barrial.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropietarioListFinderOutputPort {
    Page<Propietario> findAll(Pageable pageable);
}
