package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioMother;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PropietarioByIdUpdaterAdapterTest {

    private PropietarioRepository repository;
    private PropietarioOutputAdapterMapper mapper;
    private PropietarioByIdUpdaterAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(PropietarioRepository.class);
        mapper = new PropietarioOutputAdapterMapperImpl();
        adapter = new PropietarioByIdUpdaterAdapter(repository, mapper);
    }

    @Test
    void shouldSaveEntity_whenPerform() {
        Propietario domain = PropietarioMother.valid();
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        adapter.perform(domain);

        ArgumentCaptor<PropietarioEntity> captor = ArgumentCaptor.forClass(PropietarioEntity.class);
        verify(repository).save(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(domain.getId());
        assertThat(captor.getValue().getNombre()).isEqualTo(domain.getNombre().getValor());
        assertThat(captor.getValue().getDni()).isEqualTo(domain.getDni().getValor());
    }

    @Test
    void shouldPropagateException_whenRepositoryFails() {
        Propietario domain = PropietarioMother.valid();
        when(repository.save(any())).thenThrow(new RuntimeException("Error de persistencia"));

        org.assertj.core.api.Assertions.assertThatThrownBy(() -> adapter.perform(domain))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error de persistencia");
    }
}
