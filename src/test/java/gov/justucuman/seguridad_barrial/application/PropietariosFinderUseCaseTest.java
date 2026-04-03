package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioMother;
import gov.justucuman.seguridad_barrial.domain.PropietariosFinderOutputPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropietariosFinderUseCaseTest {

    @Mock
    private PropietariosFinderOutputPort outputPort;

    @Spy
    private PropietarioByIdFinderUseCaseMapper mapper = new PropietarioByIdFinderUseCaseMapperImpl();

    @InjectMocks
    private PropietariosFinderUseCase useCase;

    @Test
    void shouldReturnAllPropietarios_whenRecordsExist() {
        Propietario p1 = PropietarioMother.valid();
        Propietario p2 = PropietarioMother.valid();
        when(outputPort.perform()).thenReturn(List.of(p1, p2));

        List<PropietarioByIdFinderResult> results = useCase.perform();

        verify(outputPort).perform();
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getId()).isEqualTo(p1.getId());
        assertThat(results.get(0).getNombre()).isEqualTo(p1.getNombre().getValor());
        assertThat(results.get(1).getId()).isEqualTo(p2.getId());
        assertThat(results.get(1).getNombre()).isEqualTo(p2.getNombre().getValor());
    }

    @Test
    void shouldReturnEmptyList_whenNoPropietariosExist() {
        when(outputPort.perform()).thenReturn(List.of());

        List<PropietarioByIdFinderResult> results = useCase.perform();

        verify(outputPort).perform();
        assertThat(results).isEmpty();
    }
}
