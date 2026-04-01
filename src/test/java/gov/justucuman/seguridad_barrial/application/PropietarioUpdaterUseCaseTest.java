package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioUpdaterOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PropietarioUpdaterUseCaseTest {

    private PropietarioUpdaterOutputPort outputPort;
    private PropietarioUpdaterUseCaseMapper mapper;
    private PropietarioUpdaterUseCase useCase;

    @BeforeEach
    void setUp() {
        outputPort = mock(PropietarioUpdaterOutputPort.class);
        mapper = new PropietarioUpdaterUseCaseMapper();
        useCase = new PropietarioUpdaterUseCase(outputPort, mapper);
    }

    @Test
    void shouldUpdatePropietario_whenValidCommandProvided() {
        UUID id = UUID.randomUUID();
        Propietario existing = PropietarioMother.withId(id);
        when(outputPort.findById(id)).thenReturn(existing);

        int dni = ThreadLocalRandom.current().nextInt(1000000, 99999999);
        PropietarioUpdaterCommand command = PropietarioUpdaterCommand.builder()
                .id(id)
                .nombre("Nombre-" + UUID.randomUUID().toString().substring(0, 8))
                .apellido("Apellido-" + UUID.randomUUID().toString().substring(0, 8))
                .dni(String.valueOf(dni))
                .direccion("Calle " + UUID.randomUUID().toString().substring(0, 8))
                .telefono(String.valueOf(ThreadLocalRandom.current().nextLong(1000000000L, 9999999999L)))
                .email(UUID.randomUUID().toString().substring(0, 8) + "@example.com")
                .build();

        useCase.perform(command);

        ArgumentCaptor<Propietario> captor = ArgumentCaptor.forClass(Propietario.class);
        verify(outputPort).update(captor.capture());
        Propietario actualizado = captor.getValue();
        assertThat(actualizado.getId()).isEqualTo(id);
        assertThat(actualizado.getNombre().getValor()).isEqualTo(command.getNombre());
        assertThat(actualizado.getApellido().getValor()).isEqualTo(command.getApellido());
        assertThat(actualizado.getDni().getValor()).isEqualTo(command.getDni());
        assertThat(actualizado.getDireccion().getValor()).isEqualTo(command.getDireccion());
        assertThat(actualizado.getTelefono().getValor()).isEqualTo(command.getTelefono());
        assertThat(actualizado.getEmail().getValor()).isEqualTo(command.getEmail());
    }

    @Test
    void shouldPropagateException_whenOutputPortFails() {
        UUID id = UUID.randomUUID();
        when(outputPort.findById(id)).thenThrow(new RuntimeException("Error de persistencia"));

        PropietarioUpdaterCommand command = PropietarioUpdaterCommand.builder()
                .id(id)
                .nombre("Nombre")
                .apellido("Apellido")
                .dni("12345678")
                .direccion("Calle Falsa")
                .build();

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> useCase.perform(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error de persistencia");
    }
}
