package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropiedadByPropietarioIdFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/propietarios/{propietarioId}/propiedades")
@RequiredArgsConstructor
public class PropiedadByPropietarioIdGetAdapter {

    private final PropiedadByPropietarioIdFinder useCase;
    private final PropiedadByIdFinderAdapterMapper mapper;

    @GetMapping
    public ResponseEntity<List<PropiedadByIdFinderResponse>> perform(@PathVariable UUID propietarioId) {
        log.info("Buscando propiedades del propietario con id: {}", propietarioId);
        List<PropiedadByIdFinderResponse> response = useCase.perform(propietarioId)
                .stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
}
