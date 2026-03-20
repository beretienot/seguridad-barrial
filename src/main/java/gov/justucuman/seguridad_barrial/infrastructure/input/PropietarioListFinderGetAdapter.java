package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioListFinder;
import gov.justucuman.seguridad_barrial.application.PropietarioFinderResult;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioFinderResponse;
import gov.justucuman.seguridad_barrial.infrastructure.input.mapper.PropietarioFinderAdapterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/propietarios")
@RequiredArgsConstructor
public class PropietarioListFinderGetAdapter {

    private final PropietarioListFinder useCase;
    private final PropietarioFinderAdapterMapper mapper;

    @GetMapping
    public ResponseEntity<Page<PropietarioFinderResponse>> findAll(Pageable pageable) {
        log.info("Buscando todos los propietarios paginados");
        Page<PropietarioFinderResult> resultPage = useCase.perform(pageable);
        Page<PropietarioFinderResponse> responsePage = resultPage.map(mapper::toResponse);
        return ResponseEntity.ok(responsePage);
    }
}
