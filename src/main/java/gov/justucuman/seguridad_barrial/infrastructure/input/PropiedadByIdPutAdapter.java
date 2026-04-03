package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropiedadByIdUpdater;
import gov.justucuman.seguridad_barrial.application.PropiedadByIdUpdaterCommand;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/propiedades")
@RequiredArgsConstructor
public class PropiedadByIdPutAdapter {

    private final PropiedadByIdUpdater useCase;
    private final PropiedadByIdUpdaterAdapterMapper mapper;

    @PutMapping("/{id}")
    public ResponseEntity<Void> perform(@PathVariable UUID id,
                                        @Valid @RequestBody PropiedadByIdUpdaterRequest request) {
        log.info("Recibiendo solicitud para actualizar propiedad con id: {}", id);
        PropiedadByIdUpdaterCommand command = mapper.toCommand(request, id);
        useCase.perform(command);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
