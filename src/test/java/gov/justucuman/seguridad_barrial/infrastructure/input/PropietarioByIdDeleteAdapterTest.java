package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioRemover;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropietarioRemoverDeleteAdapter.class)
class PropietarioRemoverDeleteAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PropietarioRemover useCase;

    @Test
    void shouldReturnNoContent_whenValidId() throws Exception {
        UUID id = UUID.randomUUID();
        mockMvc.perform(delete("/api/propietarios/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(useCase).perform(id);
    }
}
