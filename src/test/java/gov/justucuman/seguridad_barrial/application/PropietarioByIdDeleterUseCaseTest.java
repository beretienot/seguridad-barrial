
package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.PropietarioRemoverOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class PropietarioRemoverUseCaseTest {
    private PropietarioRemoverOutputPort outputPort;
    private PropietarioRemoverUseCase useCase;

    @BeforeEach
    void setUp() {
        outputPort = mock(PropietarioRemoverOutputPort.class);
        useCase = new PropietarioRemoverUseCase(outputPort);
    }

    @Test
    void shouldDeleteById_whenValidIdProvided() {
        UUID id = UUID.randomUUID();
        useCase.perform(id);
        verify(outputPort).deleteById(id);
    }
}
