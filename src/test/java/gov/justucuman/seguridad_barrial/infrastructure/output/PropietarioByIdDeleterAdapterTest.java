
package gov.justucuman.seguridad_barrial.infrastructure.output;
import java.util.UUID;

import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

class PropietarioRemoverAdapterTest {
    private PropietarioRepository repository;
    private PropietarioRemoverAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(PropietarioRepository.class);
        adapter = new PropietarioRemoverAdapter(repository);
    }

    @Test
    void shouldDeleteById() {
        UUID id = UUID.randomUUID();
        adapter.deleteById(id);
        verify(repository).deleteById(id);
    }
}
