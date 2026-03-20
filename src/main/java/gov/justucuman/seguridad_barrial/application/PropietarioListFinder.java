package gov.justucuman.seguridad_barrial.application;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PropietarioListFinder {
    Page<PropietarioFinderResult> perform(Pageable pageable);
}
