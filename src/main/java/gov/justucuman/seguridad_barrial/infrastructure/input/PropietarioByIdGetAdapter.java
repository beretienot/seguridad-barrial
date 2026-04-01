package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioByIdFinder;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdFinderResult;
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
public class PropietarioByIdGetAdapter {

    private final PropietarioByIdFinder useCase;
    private final PropietarioByIdFinderAdapterMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<PropietarioByIdFinderResponse> perform(@PathVariable UUID id) {
        log.info("Buscando propietario con id: {}", id);
        PropietarioByIdFinderResult result = useCase.perform(id);
        PropietarioByIdFinderResponse response = mapper.toResponse(result);
        return ResponseEntity.ok(response);
    }
}
