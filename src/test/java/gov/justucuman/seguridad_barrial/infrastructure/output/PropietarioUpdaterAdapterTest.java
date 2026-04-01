package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioNotFoundException;
import gov.justucuman.seguridad_barrial.domain.PropietarioMother;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PropietarioUpdaterAdapterTest {

    private PropietarioRepository repository;
    private PropietarioOutputAdapterMapper mapper;
    private PropietarioUpdaterAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(PropietarioRepository.class);
        mapper = new PropietarioOutputAdapterMapperImpl();
        adapter = new PropietarioUpdaterAdapter(repository, mapper);
    }

    @Test
    void shouldFindById_whenExists() {
        UUID id = UUID.randomUUID();
        Propietario domain = PropietarioMother.withId(id);
        PropietarioEntity entity = mapper.toEntity(domain);
        when(repository.findById(id)).thenReturn(Optional.of(entity));

        Propietario result = adapter.findById(id);

        assertThat(result.getId()).isEqualTo(id);
        assertThat(result.getNombre().getValor()).isEqualTo(domain.getNombre().getValor());
        assertThat(result.getDni().getValor()).isEqualTo(domain.getDni().getValor());
    }

    @Test
    void shouldThrowPropietarioNotFoundException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.findById(id))
                .isInstanceOf(PropietarioNotFoundException.class);
    }

    @Test
    void shouldSaveEntity_whenUpdate() {
        Propietario domain = PropietarioMother.valid();
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        adapter.update(domain);

        ArgumentCaptor<PropietarioEntity> captor = ArgumentCaptor.forClass(PropietarioEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(domain.getId());
        assertThat(captor.getValue().getNombre()).isEqualTo(domain.getNombre().getValor());
        assertThat(captor.getValue().getDni()).isEqualTo(domain.getDni().getValor());
    }
}
