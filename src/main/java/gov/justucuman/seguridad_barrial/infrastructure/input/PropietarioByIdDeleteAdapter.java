package gov.justucuman.seguridad_barrial.infrastructure.input;

import io.swagger.v3.oas.annotations.tags.Tag;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdDeleter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Propietarios")
@Slf4j
@RestController
@RequestMapping("/api/propietarios")
@RequiredArgsConstructor
public class PropietarioByIdDeleteAdapter {

    private final PropietarioByIdDeleter useCase;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> perform(@PathVariable UUID id) {
        log.info("Recibiendo solicitud para eliminar propietario con id: {}", id);
        useCase.perform(id);
        log.info("Propietario eliminado exitosamente con id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
