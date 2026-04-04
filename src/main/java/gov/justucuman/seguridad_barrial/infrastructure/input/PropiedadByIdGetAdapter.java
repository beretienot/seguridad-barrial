package gov.justucuman.seguridad_barrial.infrastructure.input;

import io.swagger.v3.oas.annotations.tags.Tag;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdFinder;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdFinderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Propiedades")
@Slf4j
@RestController
@RequestMapping("/api/propiedades")
@RequiredArgsConstructor
public class PropiedadByIdGetAdapter {

    private final PropiedadByIdFinder useCase;
    private final PropiedadByIdFinderAdapterMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<PropiedadByIdFinderResponse> perform(@PathVariable UUID id) {
        log.info("Buscando propiedad con id: {}", id);
        PropiedadByIdFinderResult result = useCase.perform(id);
        return ResponseEntity.ok(mapper.toResponse(result));
    }
}
