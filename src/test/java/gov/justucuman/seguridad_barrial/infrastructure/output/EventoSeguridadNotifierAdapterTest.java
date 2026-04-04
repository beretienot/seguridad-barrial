package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.DescripcionEventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.Direccion;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.FechaEventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.Propiedad;
import gov.justucuman.seguridad_barrial.domain.PropiedadId;
import gov.justucuman.seguridad_barrial.domain.PropietarioId;
import gov.justucuman.seguridad_barrial.domain.TipoEventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.UbicacionGps;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventoSeguridadNotifierAdapterTest {

    @Mock
    private EventoSeguridadStreamBroker streamBroker;

    @InjectMocks
    private EventoSeguridadNotifierAdapter adapter;

    @Test
    void shouldNotifyStreamBrokerWithCoordinates_whenPropiedadHasUbicacion() {
        UUID propiedadId = UUID.randomUUID();
        EventoSeguridad evento = new EventoSeguridad(
                UUID.randomUUID(),
                new PropiedadId(propiedadId),
                new TipoEventoSeguridad("ROBO"),
                new DescripcionEventoSeguridad("Intento de ingreso"),
                new FechaEventoSeguridad(LocalDateTime.now().withNano(0))
        );
        Propiedad propiedad = new Propiedad(
                propiedadId,
                new PropietarioId(UUID.randomUUID()),
                new Direccion("Direccion valida 123"),
                null,
                null,
                new UbicacionGps(new BigDecimal("-26.82411"), new BigDecimal("-65.22265"))
        );

        adapter.perform(evento, propiedad);

        ArgumentCaptor<EventoSeguridadStreamMessage> captor = ArgumentCaptor.forClass(EventoSeguridadStreamMessage.class);
        verify(streamBroker).notify(captor.capture());

        EventoSeguridadStreamMessage message = captor.getValue();
        assertThat(message.id()).isEqualTo(evento.getId());
        assertThat(message.propiedadId()).isEqualTo(propiedadId);
        assertThat(message.tipo()).isEqualTo("ROBO");
        assertThat(message.descripcion()).isEqualTo("Intento de ingreso");
        assertThat(message.latitud()).isEqualByComparingTo("-26.82411");
        assertThat(message.longitud()).isEqualByComparingTo("-65.22265");
    }
}
