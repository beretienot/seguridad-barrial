package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioCreatorOutputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PropietarioCreatorUseCaseTest {

    @Mock
    private PropietarioCreatorOutputPort outputPort;

    @Spy
    private PropietarioCreatorUseCaseMapper mapper = new PropietarioCreatorUseCaseMapperImpl();

    @InjectMocks
    private PropietarioCreatorUseCase useCase;

    @Test
    void shouldInvokeOutputPort_whenValidCommand() {
        var command = PropietarioCreatorCommandMother.valid();
        doNothing().when(outputPort).perform(any(Propietario.class));

        useCase.perform(command);

        ArgumentCaptor<Propietario> captor = ArgumentCaptor.forClass(Propietario.class);
        verify(outputPort).perform(captor.capture());

        Propietario captured = captor.getValue();
        assertThat(captured.getId()).isEqualTo(command.getId());
        assertThat(captured.getNombre().getValor()).isEqualTo("Juan");
        assertThat(captured.getApellido().getValor()).isEqualTo("Perez");
        assertThat(captured.getDni().getValor()).isEqualTo("12345678");
        assertThat(captured.getDireccion().getValor()).isEqualTo("Calle Falsa 123");
        assertThat(captured.getTelefono().getValor()).isEqualTo("3814567890");
        assertThat(captured.getEmail().getValor()).isEqualTo("juan.perez@email.com");
    }

    @Test
    void shouldThrowException_whenDniIsInvalid() {
        var command = PropietarioCreatorCommandMother.withInvalidDni();

        assertThatThrownBy(() -> useCase.perform(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("DNI");
    }

    @Test
    void shouldThrowException_whenNombreIsNull() {
        var command = PropietarioCreatorCommandMother.withoutNombre();

        assertThatThrownBy(() -> useCase.perform(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("nombre");
    }

    @Test
    void shouldPropagateException_whenOutputPortFails() {
        var command = PropietarioCreatorCommandMother.valid();
        doThrow(new RuntimeException("Error de persistencia")).when(outputPort).perform(any(Propietario.class));

        assertThatThrownBy(() -> useCase.perform(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error de persistencia");
    }
}
