package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioFinder;
import gov.justucuman.seguridad_barrial.application.PropietarioFinderResult;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioFinderResponse;
import gov.justucuman.seguridad_barrial.infrastructure.input.mapper.PropietarioFinderAdapterMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/propietarios")
@RequiredArgsConstructor
public class PropietarioFinderGetAdapter {

    private final PropietarioFinder useCase;
    private final PropietarioFinderAdapterMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<PropietarioFinderResponse> findById(@PathVariable UUID id) {
        log.info("Buscando propietario con id: {}", id);
        PropietarioFinderResult result = useCase.perform(id);
        PropietarioFinderResponse response = mapper.toResponse(result);
        return ResponseEntity.ok(response);
    }
}
