package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioUpdater;
import gov.justucuman.seguridad_barrial.application.PropietarioUpdaterCommand;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioUpdaterRequest;
import gov.justucuman.seguridad_barrial.infrastructure.input.mapper.PropietarioUpdaterAdapterMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/propietarios")
@RequiredArgsConstructor
public class PropietarioUpdaterPutAdapter {

    private final PropietarioUpdater useCase;
    private final PropietarioUpdaterAdapterMapper mapper;

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody PropietarioUpdaterRequest request) {
        log.info("Actualizando propietario con id: {}", id);
        PropietarioUpdaterCommand command = mapper.toCommand(id, request);
        useCase.perform(command);
        log.info("Propietario actualizado exitosamente con id: {}", id);
        return ResponseEntity.noContent().build();
    }
}
