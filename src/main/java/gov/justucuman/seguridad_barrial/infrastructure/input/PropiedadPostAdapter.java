package gov.justucuman.seguridad_barrial.infrastructure.input;

import io.swagger.v3.oas.annotations.tags.Tag;
import gov.justucuman.seguridad_barrial.application.PropiedadCreator;
import gov.justucuman.seguridad_barrial.application.PropiedadCreatorCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Propiedades")
@Slf4j
@RestController
@RequestMapping("/api/propietarios/{propietarioId}/propiedades")
@RequiredArgsConstructor
public class PropiedadPostAdapter {

    private final PropiedadCreator useCase;
    private final PropiedadCreatorAdapterMapper mapper;

    @PostMapping
    public ResponseEntity<Void> perform(@PathVariable UUID propietarioId,
                                        @Valid @RequestBody PropiedadCreatorRequest request) {
        log.info("Recibiendo solicitud para crear propiedad con id: {} para propietario: {}", request.getId(), propietarioId);
        PropiedadCreatorCommand command = mapper.toCommand(request, propietarioId);
        useCase.perform(command);
        log.info("Propiedad creada exitosamente con id: {}", request.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
