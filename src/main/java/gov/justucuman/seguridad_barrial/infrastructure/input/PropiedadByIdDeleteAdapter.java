package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropiedadByIdDeleter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/propiedades")
@RequiredArgsConstructor
public class PropiedadByIdDeleteAdapter {

    private final PropiedadByIdDeleter useCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> perform(@PathVariable UUID id) {
        log.info("Recibiendo solicitud para eliminar propiedad con id: {}", id);
        useCase.perform(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
