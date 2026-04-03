package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioByIdFinderOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioByIdFinderOutputAdapterMapperImpl;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntityMother;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PropietariosFinderAdapterTest {

    private PropietarioRepository repository;
    private PropietarioByIdFinderOutputAdapterMapper mapper;
    private PropietariosFinderAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(PropietarioRepository.class);
        mapper = new PropietarioByIdFinderOutputAdapterMapperImpl();
        adapter = new PropietariosFinderAdapter(repository, mapper);
    }

    @Test
    void shouldReturnAllPropietarios_whenRecordsExist() {
        PropietarioEntity e1 = PropietarioEntityMother.valid();
        PropietarioEntity e2 = PropietarioEntityMother.valid();
        when(repository.findAll()).thenReturn(List.of(e1, e2));

        List<Propietario> results = adapter.perform();

        verify(repository).findAll();
        assertThat(results).hasSize(2);
        assertThat(results.get(0).getId()).isEqualTo(e1.getId());
        assertThat(results.get(0).getNombre().getValor()).isEqualTo(e1.getNombre());
        assertThat(results.get(1).getId()).isEqualTo(e2.getId());
        assertThat(results.get(1).getNombre().getValor()).isEqualTo(e2.getNombre());
    }

    @Test
    void shouldReturnEmptyList_whenNoPropietariosExist() {
        when(repository.findAll()).thenReturn(List.of());

        List<Propietario> results = adapter.perform();

        verify(repository).findAll();
        assertThat(results).isEmpty();
    }
}
