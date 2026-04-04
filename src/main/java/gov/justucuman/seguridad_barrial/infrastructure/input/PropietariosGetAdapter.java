package gov.justucuman.seguridad_barrial.infrastructure.input;

import io.swagger.v3.oas.annotations.tags.Tag;
import gov.justucuman.seguridad_barrial.application.PropietariosFinder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Propietarios")
@Slf4j
@RestController
@RequestMapping("/api/propietarios")
@RequiredArgsConstructor
public class PropietariosGetAdapter {

    private final PropietariosFinder useCase;
    private final PropietariosFinderAdapterMapper mapper;

    @GetMapping
    public ResponseEntity<List<PropietarioByIdFinderResponse>> perform() {
        log.info("Recibiendo solicitud para listar todos los propietarios");
        List<PropietarioByIdFinderResponse> response = mapper.toResponse(useCase.perform());
        log.info("Se retornan {} propietarios", response.size());
        return ResponseEntity.ok(response);
    }
}
