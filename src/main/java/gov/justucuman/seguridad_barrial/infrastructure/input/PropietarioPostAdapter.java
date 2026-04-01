package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioCreator;
import gov.justucuman.seguridad_barrial.application.PropietarioCreatorCommand;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioCreatorRequest;
import gov.justucuman.seguridad_barrial.infrastructure.input.mapper.PropietarioCreatorAdapterMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/propietarios")
@RequiredArgsConstructor
public class PropietarioCreatorPostAdapter {

    private final PropietarioCreator useCase;
    private final PropietarioCreatorAdapterMapper mapper;

    @PostMapping
    public ResponseEntity<Void> perform(@Valid @RequestBody PropietarioCreatorRequest request) {
        log.info("Recibiendo solicitud para crear propietario con id: {}", request.getId());
        PropietarioCreatorCommand command = mapper.toCommand(request);
        useCase.perform(command);
        log.info("Propietario creado exitosamente con id: {}", request.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
