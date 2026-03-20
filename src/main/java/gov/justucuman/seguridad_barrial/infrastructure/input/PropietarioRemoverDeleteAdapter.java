package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioRemover;
import gov.justucuman.seguridad_barrial.infrastructure.input.mapper.PropietarioRemoverAdapterMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/propietarios")
@RequiredArgsConstructor
public class PropietarioRemoverDeleteAdapter {
    private final PropietarioRemover useCase;
    @SuppressWarnings("unused")
    private final PropietarioRemoverAdapterMapper mapper;

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        log.info("Eliminando propietario con id: {}", id);
        useCase.perform(id);
        log.info("Propietario eliminado exitosamente con id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
