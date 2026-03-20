package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioUpdaterOutputPort;
import gov.justucuman.seguridad_barrial.domain.mother.PropietarioMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class PropietarioUpdaterUseCaseTest {
    private PropietarioUpdaterOutputPort outputPort;
    private PropietarioUpdaterUseCase useCase;

    @BeforeEach
    void setUp() {
        outputPort = mock(PropietarioUpdaterOutputPort.class);
        useCase = new PropietarioUpdaterUseCase(outputPort);
    }

    @Test
    void shouldUpdatePropietario_whenValidCommandProvided() {
        UUID id = UUID.randomUUID();
        Propietario original = PropietarioMother.withId(id);
        when(outputPort.findById(id)).thenReturn(original);

        PropietarioUpdaterCommand command = PropietarioUpdaterCommand.builder()
                .id(id)
                .nombre("NuevoNombre")
                .apellido("NuevoApellido")
                .dni("12345678")
                .direccion("Nueva Direccion")
                .telefono("3811234567")
                .email("nuevo@email.com")
                .build();

        useCase.perform(command);

        ArgumentCaptor<Propietario> captor = ArgumentCaptor.forClass(Propietario.class);
        verify(outputPort).update(captor.capture());
        Propietario actualizado = captor.getValue();
        assertThat(actualizado.getId()).isEqualTo(id);
        assertThat(actualizado.getNombre().getValor()).isEqualTo("NuevoNombre");
        assertThat(actualizado.getApellido().getValor()).isEqualTo("NuevoApellido");
        assertThat(actualizado.getDni().getValor()).isEqualTo("12345678");
        assertThat(actualizado.getDireccion().getValor()).isEqualTo("Nueva Direccion");
        assertThat(actualizado.getTelefono().getValor()).isEqualTo("3811234567");
        assertThat(actualizado.getEmail().getValor()).isEqualTo("nuevo@email.com");
    }
}
