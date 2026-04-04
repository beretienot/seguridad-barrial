package gov.justucuman.seguridad_barrial.application;

import gov.justucuman.seguridad_barrial.domain.Direccion;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridadCreatorOutputPort;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridadNotifierOutputPort;
import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadByIdFinderOutputPort;
import gov.justucuman.seguridad_barrial.domain.PropietarioId;
import gov.justucuman.seguridad_barrial.domain.UbicacionGps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventoSeguridadCreatorUseCaseTest {

    @Mock
    private PropiedadByIdFinderOutputPort propiedadFinderPort;

    @Mock
    private EventoSeguridadCreatorOutputPort outputPort;

    @Mock
    private EventoSeguridadNotifierOutputPort notifierOutputPort;

    @Spy
    private EventoSeguridadCreatorUseCaseMapper mapper = Mappers.getMapper(EventoSeguridadCreatorUseCaseMapper.class);

    @InjectMocks
    private EventoSeguridadCreatorUseCase useCase;

    @Test
    void shouldInvokeOutputPort_whenValidCommand() {
        var command = EventoSeguridadCreatorCommandMother.valid();
        when(propiedadFinderPort.findById(command.getPropiedadId())).thenReturn(propiedadWithId(command.getPropiedadId()));

        EventoSeguridadCreatorResult result = useCase.perform(command);

        ArgumentCaptor<EventoSeguridad> captor = ArgumentCaptor.forClass(EventoSeguridad.class);
        verify(outputPort).perform(captor.capture());

        EventoSeguridad captured = captor.getValue();
        assertThat(captured.getId()).isEqualTo(command.getId());
        assertThat(captured.getPropiedadId().getValor()).isEqualTo(command.getPropiedadId());
        assertThat(captured.getTipo().getValor()).isEqualTo(command.getTipo());
        assertThat(captured.getDescripcion().getValor()).isEqualTo(command.getDescripcion());
        assertThat(captured.getFecha().getValor()).isNotNull();

        assertThat(result.getId()).isEqualTo(command.getId());
        assertThat(result.getPropiedadId()).isEqualTo(command.getPropiedadId());
        assertThat(result.getFecha()).isEqualTo(captured.getFecha().getValor());
        assertThat(result.getLatitud()).isEqualByComparingTo("-26.82411");
        assertThat(result.getLongitud()).isEqualByComparingTo("-65.22265");
        verify(notifierOutputPort).perform(any(EventoSeguridad.class), any(Propiedad.class));
    }

    @Test
    void shouldThrowException_whenTipoIsBlank() {
        var command = EventoSeguridadCreatorCommandMother.withBlankTipo();
        when(propiedadFinderPort.findById(command.getPropiedadId())).thenReturn(propiedadWithId(command.getPropiedadId()));

        assertThatThrownBy(() -> useCase.perform(command))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("tipo");
    }

    @Test
    void shouldPropagateException_whenOutputPortFails() {
        var command = EventoSeguridadCreatorCommandMother.valid();
        when(propiedadFinderPort.findById(command.getPropiedadId())).thenReturn(propiedadWithId(command.getPropiedadId()));
        doThrow(new RuntimeException("Error de persistencia")).when(outputPort).perform(any(EventoSeguridad.class));

        assertThatThrownBy(() -> useCase.perform(command))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error de persistencia");
    }

    private Propiedad propiedadWithId(UUID propiedadId) {
        return new Propiedad(
                propiedadId,
                new PropietarioId(UUID.randomUUID()),
                new Direccion("Direccion valida 123"),
                null,
                null,
                new UbicacionGps(
                    new BigDecimal("-26.82411"),
                    new BigDecimal("-65.22265")
                )
        );
    }
}
