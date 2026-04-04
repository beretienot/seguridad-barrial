package gov.justucuman.seguridad_barrial.application;

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
public class EventoSeguridadCreatorCommand {

    private UUID id;
    private UUID propiedadId;
    private String tipo;
    private String descripcion;
}
