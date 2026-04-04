package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.EventoSeguridad;
import gov.justucuman.seguridad_barrial.domain.EventoSeguridadMother;
import gov.justucuman.seguridad_barrial.domain.PropiedadNotFoundException;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.EventoSeguridadCreatorOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.EventoSeguridadEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.EventoSeguridadRepository;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropiedadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventoSeguridadCreatorAdapterTest {

    @Mock
    private EventoSeguridadRepository eventoSeguridadRepository;

    @Mock
    private PropiedadRepository propiedadRepository;

    @Spy
    private EventoSeguridadCreatorOutputAdapterMapper mapper = Mappers.getMapper(EventoSeguridadCreatorOutputAdapterMapper.class);

    @InjectMocks
    private EventoSeguridadCreatorAdapter adapter;

    @Captor
    private ArgumentCaptor<EventoSeguridadEntity> entityCaptor;

    @Test
    void shouldPersistEntity_whenValidEventoSeguridad() {
        UUID propiedadId = UUID.randomUUID();
        EventoSeguridad evento = EventoSeguridadMother.withPropiedadId(propiedadId);
        PropiedadEntity propiedadEntity = PropiedadEntity.builder().id(propiedadId).build();

        when(propiedadRepository.findById(propiedadId)).thenReturn(Optional.of(propiedadEntity));
        when(eventoSeguridadRepository.save(any(EventoSeguridadEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        adapter.perform(evento);

        verify(eventoSeguridadRepository).save(entityCaptor.capture());
        EventoSeguridadEntity saved = entityCaptor.getValue();
        assertThat(saved.getId()).isEqualTo(evento.getId());
        assertThat(saved.getPropiedad().getId()).isEqualTo(propiedadId);
        assertThat(saved.getTipo()).isEqualTo(evento.getTipo().getValor());
        assertThat(saved.getDescripcion()).isEqualTo(evento.getDescripcion().getValor());
        assertThat(saved.getFechaEvento()).isEqualTo(evento.getFecha().getValor());
    }

    @Test
    void shouldThrowException_whenPropiedadDoesNotExist() {
        EventoSeguridad evento = EventoSeguridadMother.valid();
        when(propiedadRepository.findById(evento.getPropiedadId().getValor())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.perform(evento))
                .isInstanceOf(PropiedadNotFoundException.class)
                .hasMessageContaining("Propiedad no encontrada");
    }

    @Test
    void shouldPropagateException_whenRepositoryFails() {
        EventoSeguridad evento = EventoSeguridadMother.valid();
        PropiedadEntity propiedadEntity = PropiedadEntity.builder().id(evento.getPropiedadId().getValor()).build();

        when(propiedadRepository.findById(evento.getPropiedadId().getValor())).thenReturn(Optional.of(propiedadEntity));
        when(eventoSeguridadRepository.save(any(EventoSeguridadEntity.class)))
                .thenThrow(new RuntimeException("Error de persistencia"));

        assertThatThrownBy(() -> adapter.perform(evento))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error de persistencia");
    }
}
