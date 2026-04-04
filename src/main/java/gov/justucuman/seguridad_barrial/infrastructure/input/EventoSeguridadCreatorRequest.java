package gov.justucuman.seguridad_barrial.infrastructure.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoSeguridadCreatorRequest {

    @NotNull
    private UUID id;

    @NotBlank
    private String tipo;

    @NotBlank
    private String descripcion;
}
