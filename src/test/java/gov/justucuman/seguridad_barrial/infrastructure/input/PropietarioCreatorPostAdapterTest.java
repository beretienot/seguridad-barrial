package gov.justucuman.seguridad_barrial.infrastructure.input;

import tools.jackson.databind.ObjectMapper;
import gov.justucuman.seguridad_barrial.application.PropietarioCreator;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.PropietarioCreatorRequestMother;
import gov.justucuman.seguridad_barrial.infrastructure.input.mapper.PropietarioCreatorAdapterMapperImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropietarioCreatorPostAdapter.class)
@Import(PropietarioCreatorAdapterMapperImpl.class)
class PropietarioCreatorPostAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PropietarioCreator useCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnCreatedWithNoBody_whenValidRequest() throws Exception {
        var request = PropietarioCreatorRequestMother.valid();

        doNothing().when(useCase).perform(any());

        mockMvc.perform(post("/api/propietarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));

        verify(useCase).perform(any());
    }

    @Test
    void shouldReturnBadRequest_whenNombreIsBlank() throws Exception {
        var request = PropietarioCreatorRequestMother.withoutNombre();

        mockMvc.perform(post("/api/propietarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenInvalidEmail() throws Exception {
        var request = PropietarioCreatorRequestMother.withInvalidEmail();

        mockMvc.perform(post("/api/propietarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenIdIsNull() throws Exception {
        var request = PropietarioCreatorRequestMother.withoutId();

        mockMvc.perform(post("/api/propietarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnInternalServerError_whenUseCaseThrowsException() throws Exception {
        var request = PropietarioCreatorRequestMother.valid();

        doThrow(new RuntimeException("Error inesperado")).when(useCase).perform(any());

        mockMvc.perform(post("/api/propietarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}
