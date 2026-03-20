
package gov.justucuman.seguridad_barrial.infrastructure.output;
import java.util.UUID;

import gov.justucuman.seguridad_barrial.domain.PropietarioRemoverOutputPort;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropietarioRemoverAdapter implements PropietarioRemoverOutputPort {
    private final PropietarioRepository repository;

    @Override
    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
