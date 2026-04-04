package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.EventoSeguridadCreator;
import gov.justucuman.seguridad_barrial.application.EventoSeguridadCreatorCommand;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Eventos de Seguridad")
@Slf4j
@RestController
@RequestMapping("/api/propiedades/{propiedadId}/eventos-seguridad")
@RequiredArgsConstructor
public class EventoSeguridadPostAdapter {

    private final EventoSeguridadCreator useCase;
    private final EventoSeguridadCreatorAdapterMapper mapper;

    @PostMapping
    public ResponseEntity<Void> perform(@PathVariable UUID propiedadId,
                                        @Valid @RequestBody EventoSeguridadCreatorRequest request) {
        log.info("Recibiendo solicitud para registrar evento de seguridad con id: {} en propiedad: {}",
                request.getId(), propiedadId);
        EventoSeguridadCreatorCommand command = mapper.toCommand(request, propiedadId);
        useCase.perform(command);
        log.info("Evento de seguridad registrado exitosamente con id: {}", request.getId());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
