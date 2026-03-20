
package gov.justucuman.seguridad_barrial.infrastructure.output;
import java.util.UUID;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioUpdaterOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropietarioUpdaterAdapter implements PropietarioUpdaterOutputPort {
    private final PropietarioRepository repository;
    private final PropietarioOutputAdapterMapper mapper;

    @Override
    public Propietario findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toDomain)
                .orElseThrow(() -> new IllegalArgumentException("Propietario no encontrado: " + id));
    }

    @Override
    public void update(Propietario propietario) {
        repository.save(mapper.toEntity(propietario));
    }
}
