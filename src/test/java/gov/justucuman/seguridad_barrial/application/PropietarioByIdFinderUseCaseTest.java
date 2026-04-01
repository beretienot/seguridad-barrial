package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioByIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioMother;
import gov.justucuman.seguridad_barrial.domain.PropietarioNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropietarioByIdFinderUseCaseTest {

    @Mock
    private PropietarioByIdFinderOutputPort outputPort;

    @Spy
    private PropietarioByIdFinderUseCaseMapper mapper = new PropietarioByIdFinderUseCaseMapperImpl();

    @InjectMocks
    private PropietarioByIdFinderUseCase useCase;

    @Test
    void shouldReturnResult_whenPropietarioExists() {
        UUID id = UUID.randomUUID();
        Propietario propietario = PropietarioMother.withId(id);
        when(outputPort.findById(id)).thenReturn(propietario);

        PropietarioByIdFinderResult result = useCase.perform(id);

        verify(outputPort).findById(id);
        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getNombre()).isEqualTo(propietario.getNombre().getValor());
        assertThat(result.getApellido()).isEqualTo(propietario.getApellido().getValor());
        assertThat(result.getDni()).isEqualTo(propietario.getDni().getValor());
        assertThat(result.getDireccion()).isEqualTo(propietario.getDireccion().getValor());
        assertThat(result.getTelefono()).isEqualTo(propietario.getTelefono().getValor());
        assertThat(result.getEmail()).isEqualTo(propietario.getEmail().getValor());
    }

    @Test
    void shouldReturnResultWithNulls_whenOptionalFieldsAbsent() {
        Propietario propietario = PropietarioMother.withoutOptionalFields();
        when(outputPort.findById(propietario.getId())).thenReturn(propietario);

        PropietarioByIdFinderResult result = useCase.perform(propietario.getId());

        assertThat(result.getTelefono()).isNull();
        assertThat(result.getEmail()).isNull();
    }

    @Test
    void shouldPropagateException_whenOutputPortThrowsNotFoundException() {
        UUID id = UUID.randomUUID();
        when(outputPort.findById(id))
                .thenThrow(new PropietarioNotFoundException("Propietario no encontrado con id: " + id));

        assertThatThrownBy(() -> useCase.perform(id))
                .isInstanceOf(PropietarioNotFoundException.class)
                .hasMessageContaining(id.toString());
    }
}
