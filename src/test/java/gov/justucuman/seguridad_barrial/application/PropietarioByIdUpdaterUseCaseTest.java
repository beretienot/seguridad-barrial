package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdUpdaterOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class PropietarioByIdUpdaterUseCaseTest {

    private PropietarioByIdFinderOutputPort finderPort;
    private PropietarioByIdUpdaterOutputPort updaterPort;
    private PropietarioByIdUpdaterUseCaseMapper mapper;
    private PropietarioByIdUpdaterUseCase useCase;

    @BeforeEach
    void setUp() {
        finderPort = mock(PropietarioByIdFinderOutputPort.class);
        updaterPort = mock(PropietarioByIdUpdaterOutputPort.class);
        mapper = new PropietarioByIdUpdaterUseCaseMapper();
        useCase = new PropietarioByIdUpdaterUseCase(finderPort, updaterPort, mapper);
    }

    @Test
    void shouldUpdatePropietario_whenValidCommandProvided() {
        UUID id = UUID.randomUUID();
        Propietario existing = PropietarioMother.withId(id);
        when(finderPort.findById(id)).thenReturn(existing);

        int dni = ThreadLocalRandom.current().nextInt(1000000, 99999999);
        PropietarioByIdUpdaterCommand command = PropietarioByIdUpdaterCommand.builder()
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
        verify(updaterPort).perform(captor.capture());
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
    void shouldPropagateException_whenFinderPortFails() {
        UUID id = UUID.randomUUID();
        when(finderPort.findById(id)).thenThrow(new RuntimeException("Error de persistencia"));

        PropietarioByIdUpdaterCommand command = PropietarioByIdUpdaterCommand.builder()
                .id(id)
                .nombre("Nombre")
                .apellido("Apellido")
                .dni("12345678")
                .direccion("Calle Falsa")
                .build();

        assertThatThrownBy(() -> useCase.perform(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error de persistencia");
    }
}
