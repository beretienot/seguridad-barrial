
package gov.justucuman.seguridad_barrial.infrastructure.output;
import java.util.UUID;

import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

class PropietarioByIdDeleterAdapterTest {
    private PropietarioRepository repository;
    private PropietarioByIdDeleterAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(PropietarioRepository.class);
        adapter = new PropietarioByIdDeleterAdapter(repository);
    }

    @Test
    void shouldDeleteById() {
        UUID id = UUID.randomUUID();
        adapter.perform(id);
        verify(repository).deleteById(id);
    }
}
