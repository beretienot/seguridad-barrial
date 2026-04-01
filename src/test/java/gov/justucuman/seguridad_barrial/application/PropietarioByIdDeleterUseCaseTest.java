
package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.PropietarioByIdDeleterOutputPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.*;

class PropietarioByIdDeleterUseCaseTest {
    private PropietarioByIdDeleterOutputPort outputPort;
    private PropietarioByIdDeleterUseCase useCase;

    @BeforeEach
    void setUp() {
        outputPort = mock(PropietarioByIdDeleterOutputPort.class);
        useCase = new PropietarioByIdDeleterUseCase(outputPort);
    }

    @Test
    void shouldDeleteById_whenValidIdProvided() {
        UUID id = UUID.randomUUID();
        useCase.perform(id);
        verify(outputPort).perform(id);
    }
}
