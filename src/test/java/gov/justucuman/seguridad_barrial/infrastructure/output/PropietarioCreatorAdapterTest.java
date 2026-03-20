package gov.justucuman.seguridad_barrial.infrastructure.output;

import gov.justucuman.seguridad_barrial.domain.Propietario;
import gov.justucuman.seguridad_barrial.domain.PropietarioMother;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioCreatorOutputAdapterMapper;
import gov.justucuman.seguridad_barrial.infrastructure.output.mapper.PropietarioCreatorOutputAdapterMapperImpl;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioEntity;
import gov.justucuman.seguridad_barrial.infrastructure.output.persistence.PropietarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PropietarioCreatorAdapterTest {

    @Mock
    private PropietarioRepository repository;

    @Spy
    private PropietarioCreatorOutputAdapterMapper mapper = new PropietarioCreatorOutputAdapterMapperImpl();

    @InjectMocks
    private PropietarioCreatorAdapter adapter;

    @Captor
    private ArgumentCaptor<PropietarioEntity> entityCaptor;

    @Test
    void shouldPersistEntity_whenValidPropietario() {
        Propietario propietario = PropietarioMother.valid();
        when(repository.save(any(PropietarioEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        adapter.perform(propietario);

        verify(repository).save(entityCaptor.capture());
        PropietarioEntity saved = entityCaptor.getValue();
        assertThat(saved.getId()).isEqualTo(propietario.getId());
        assertThat(saved.getNombre()).isEqualTo(propietario.getNombre().getValor());
        assertThat(saved.getApellido()).isEqualTo(propietario.getApellido().getValor());
        assertThat(saved.getDni()).isEqualTo(propietario.getDni().getValor());
        assertThat(saved.getDireccion()).isEqualTo(propietario.getDireccion().getValor());
        assertThat(saved.getTelefono()).isEqualTo(propietario.getTelefono().getValor());
        assertThat(saved.getEmail()).isEqualTo(propietario.getEmail().getValor());
    }

    @Test
    void shouldPersistEntityWithNulls_whenOptionalFieldsAreNull() {
        Propietario propietario = PropietarioMother.withoutOptionalFields();
        when(repository.save(any(PropietarioEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        adapter.perform(propietario);

        verify(repository).save(entityCaptor.capture());
        PropietarioEntity saved = entityCaptor.getValue();
        assertThat(saved.getTelefono()).isNull();
        assertThat(saved.getEmail()).isNull();
    }

    @Test
    void shouldPropagateException_whenRepositoryFails() {
        Propietario propietario = PropietarioMother.valid();
        when(repository.save(any(PropietarioEntity.class)))
                .thenThrow(new RuntimeException("Error de persistencia"));

        assertThatThrownBy(() -> adapter.perform(propietario))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error de persistencia");
    }
}
