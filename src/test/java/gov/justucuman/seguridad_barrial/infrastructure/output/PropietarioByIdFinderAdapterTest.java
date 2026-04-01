package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioNotFoundException;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioByIdFinderOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioByIdFinderOutputAdapterMapperImpl;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntityMother;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PropietarioByIdFinderAdapterTest {

    private PropietarioRepository repository;
    private PropietarioByIdFinderOutputAdapterMapper mapper;
    private PropietarioByIdFinderAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(PropietarioRepository.class);
        mapper = new PropietarioByIdFinderOutputAdapterMapperImpl();
        adapter = new PropietarioByIdFinderAdapter(repository, mapper);
    }

    @Test
    void shouldReturnPropietario_whenEntityExists() {
        PropietarioEntity entity = PropietarioEntityMother.valid();
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));

        Propietario result = adapter.findById(entity.getId());

        assertThat(result.getId()).isEqualTo(entity.getId());
        assertThat(result.getNombre().getValor()).isEqualTo(entity.getNombre());
        assertThat(result.getApellido().getValor()).isEqualTo(entity.getApellido());
        assertThat(result.getDni().getValor()).isEqualTo(entity.getDni());
        assertThat(result.getDireccion().getValor()).isEqualTo(entity.getDireccion());
        assertThat(result.getTelefono().getValor()).isEqualTo(entity.getTelefono());
        assertThat(result.getEmail().getValor()).isEqualTo(entity.getEmail());
    }

    @Test
    void shouldThrowPropietarioNotFoundException_whenEntityNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> adapter.findById(id))
                .isInstanceOf(PropietarioNotFoundException.class)
                .hasMessageContaining(id.toString());
    }
}
