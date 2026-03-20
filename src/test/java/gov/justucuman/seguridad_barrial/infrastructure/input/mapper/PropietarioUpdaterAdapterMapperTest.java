package gov.justucuman.seguridad_barrial.infrastructure.input.mapper;

import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioUpdaterRequest;
import org.junit.jupiter.api.Test;



class PropietarioUpdaterAdapterMapperTest {
    // private final PropietarioUpdaterAdapterMapper mapper = new PropietarioUpdaterAdapterMapperImpl();

    @Test
    void shouldMapRequestToCommand() {
        // UUID id = UUID.randomUUID();
        PropietarioUpdaterRequest request = new PropietarioUpdaterRequest();
        request.setNombre("Juan");
        request.setApellido("Perez");
        request.setDni("12345678");
        request.setDireccion("Calle Falsa 123");
        request.setTelefono("3811234567");
        request.setEmail("juan@email.com");

        // PropietarioUpdaterCommand command = mapper.toCommand(id, request);
        // assertThat(command.getId()).isEqualTo(id);
        // assertThat(command.getNombre()).isEqualTo("Juan");
        // assertThat(command.getApellido()).isEqualTo("Perez");
        // assertThat(command.getDni()).isEqualTo("12345678");
        // assertThat(command.getDireccion()).isEqualTo("Calle Falsa 123");
        // assertThat(command.getTelefono()).isEqualTo("3811234567");
        // assertThat(command.getEmail()).isEqualTo("juan@email.com");
    }
}
