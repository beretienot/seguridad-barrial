package gov.justucuman.seguridad_barrial.infrastructure.input;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.justucuman.seguridad_barrial.application.PropietarioUpdater;
import gov.justucuman.seguridad_barrial.application.PropietarioUpdaterCommand;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioUpdaterRequest;
import gov.justucuman.seguridad_barrial.infrastructure.input.mapper.PropietarioUpdaterAdapterMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropietarioUpdaterPutAdapter.class)
class PropietarioUpdaterPutAdapterTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private PropietarioUpdater useCase;
    @MockitoBean
    private PropietarioUpdaterAdapterMapper mapper;

    private UUID id;
    private PropietarioUpdaterRequest request;
    private PropietarioUpdaterCommand command;

    @BeforeEach
    void setUp() {
        id = UUID.randomUUID();
        request = new PropietarioUpdaterRequest();
        request.setNombre("Juan");
        request.setApellido("Perez");
        request.setDni("12345678");
        request.setDireccion("Calle Falsa 123");
        request.setTelefono("3811234567");
        request.setEmail("juan@email.com");
        command = PropietarioUpdaterCommand.builder()
                .id(id)
                .nombre(request.getNombre())
                .apellido(request.getApellido())
                .dni(request.getDni())
                .direccion(request.getDireccion())
                .telefono(request.getTelefono())
                .email(request.getEmail())
                .build();
    }

    @Test
    void shouldReturnNoContent_whenValidRequest() throws Exception {
        // Mock mapping
        org.mockito.Mockito.when(mapper.toCommand(id, request)).thenReturn(command);

        mockMvc.perform(put("/api/propietarios/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<PropietarioUpdaterCommand> captor = ArgumentCaptor.forClass(PropietarioUpdaterCommand.class);
        verify(useCase).perform(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(id);
    }
}
