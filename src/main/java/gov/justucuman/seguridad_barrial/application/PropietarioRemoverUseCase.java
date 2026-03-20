package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.PropietarioRemoverOutputPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PropietarioRemoverUseCase implements PropietarioRemover {
    private final PropietarioRemoverOutputPort outputPort;

    @Override
    public void perform(UUID id) {
        outputPort.deleteById(id);
    }
}
