package gov.justucuman.seguridad_barrial.infrastructure.input;

import tools.jackson.databind.ObjectMapper;
import gov.justucuman.seguridad_barrial.application.PropietarioUpdater;
import gov.justucuman.seguridad_barrial.application.PropietarioUpdaterCommand;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioUpdaterRequest;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioUpdaterRequestMother;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropietarioUpdaterPutAdapter.class)
@Import(gov.justucuman.seguridad_barrial.infrastructure.input.mapper.PropietarioUpdaterAdapterMapperImpl.class)
class PropietarioUpdaterPutAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PropietarioUpdater useCase;

    @Test
    void shouldReturnNoContent_whenValidRequest() throws Exception {
        UUID id = UUID.randomUUID();
        PropietarioUpdaterRequest request = PropietarioUpdaterRequestMother.valid();

        mockMvc.perform(put("/api/propietarios/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<PropietarioUpdaterCommand> captor = ArgumentCaptor.forClass(PropietarioUpdaterCommand.class);
        verify(useCase).perform(captor.capture());
        assertThat(captor.getValue().getId()).isEqualTo(id);
        assertThat(captor.getValue().getNombre()).isEqualTo(request.getNombre());
        assertThat(captor.getValue().getApellido()).isEqualTo(request.getApellido());
        assertThat(captor.getValue().getDni()).isEqualTo(request.getDni());
        assertThat(captor.getValue().getDireccion()).isEqualTo(request.getDireccion());
    }

    @Test
    void shouldReturnBadRequest_whenNombreIsBlank() throws Exception {
        UUID id = UUID.randomUUID();
        PropietarioUpdaterRequest request = PropietarioUpdaterRequestMother.withoutNombre();

        mockMvc.perform(put("/api/propietarios/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
