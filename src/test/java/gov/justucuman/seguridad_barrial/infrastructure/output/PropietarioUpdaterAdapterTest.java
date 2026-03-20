
package gov.justucuman.seguridad_barrial.infrastructure.output;
import java.util.UUID;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import gov.justucuman.seguridad_barrial.domain.mother.PropietarioMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PropietarioUpdaterAdapterTest {
    private PropietarioRepository repository;
    private PropietarioOutputAdapterMapper mapper;
    private PropietarioUpdaterAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(PropietarioRepository.class);
        mapper = mock(PropietarioOutputAdapterMapper.class);
        adapter = new PropietarioUpdaterAdapter(repository, mapper);
    }

    @Test
    void shouldFindByIdAndReturnDomain() {
        UUID id = UUID.randomUUID();
        PropietarioEntity entity = PropietarioEntity.builder().id(id).build();
        Propietario domain = PropietarioMother.valid();
        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(domain);
        Propietario result = adapter.findById(id);
        assertThat(result).isEqualTo(domain);
    }

    @Test
    void shouldThrowException_whenNotFound() {
        UUID id = UUID.randomUUID();
        when(repository.findById(id)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> adapter.findById(id));
    }

    @Test
    void shouldUpdateEntity() {
        Propietario domain = PropietarioMother.valid();
        PropietarioEntity entity = PropietarioEntity.builder().id(domain.getId()).build();
        when(mapper.toEntity(domain)).thenReturn(entity);
        adapter.update(domain);
        verify(repository).save(entity);
    }
}
