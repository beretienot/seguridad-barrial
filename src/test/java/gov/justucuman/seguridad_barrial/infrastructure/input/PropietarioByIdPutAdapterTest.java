package gov.justucuman.seguridad_barrial.infrastructure.input;

import tools.jackson.databind.ObjectMapper;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdUpdater;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdUpdaterCommand;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioByIdUpdaterRequestMother;
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

@WebMvcTest(PropietarioByIdPutAdapter.class)
@Import(gov.justucuman.seguridad_barrial.infrastructure.input.PropietarioByIdUpdaterAdapterMapperImpl.class)
class PropietarioByIdPutAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PropietarioByIdUpdater useCase;

    @Test
    void shouldReturnNoContent_whenValidRequest() throws Exception {
        UUID id = UUID.randomUUID();
        PropietarioByIdUpdaterRequest request = PropietarioByIdUpdaterRequestMother.valid();

        mockMvc.perform(put("/api/propietarios/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        ArgumentCaptor<PropietarioByIdUpdaterCommand> captor = ArgumentCaptor.forClass(PropietarioByIdUpdaterCommand.class);
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
        PropietarioByIdUpdaterRequest request = PropietarioByIdUpdaterRequestMother.withoutNombre();

        mockMvc.perform(put("/api/propietarios/" + id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
