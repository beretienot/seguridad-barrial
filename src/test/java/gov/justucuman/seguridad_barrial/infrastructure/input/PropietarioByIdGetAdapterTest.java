package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioByIdFinder;
import gov.justucuman.seguridad_barrial.application.PropietarioByIdFinderResult;
import gov.justucuman.seguridad_barrial.domain.PropietarioNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropietarioByIdGetAdapter.class)
@Import(PropietarioByIdFinderAdapterMapperImpl.class)
class PropietarioByIdGetAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PropietarioByIdFinder useCase;

    @Test
    void shouldReturnOkWithBody_whenPropietarioExists() throws Exception {
        UUID id = UUID.randomUUID();
        PropietarioByIdFinderResult result = PropietarioByIdFinderResult.builder()
                .id(id)
                .nombre("Juan")
                .apellido("Perez")
                .dni("12345678")
                .direccion("Calle Falsa 123")
                .telefono("3814000000")
                .email("juan@example.com")
                .build();
        when(useCase.perform(id)).thenReturn(result);

        mockMvc.perform(get("/api/propietarios/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nombre").value("Juan"))
                .andExpect(jsonPath("$.apellido").value("Perez"))
                .andExpect(jsonPath("$.dni").value("12345678"))
                .andExpect(jsonPath("$.direccion").value("Calle Falsa 123"))
                .andExpect(jsonPath("$.telefono").value("3814000000"))
                .andExpect(jsonPath("$.email").value("juan@example.com"));
    }

    @Test
    void shouldReturnNotFound_whenPropietarioDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();
        when(useCase.perform(id))
                .thenThrow(new PropietarioNotFoundException("Propietario no encontrado con id: " + id));

        mockMvc.perform(get("/api/propietarios/" + id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnInternalServerError_whenUseCaseThrowsUnexpectedException() throws Exception {
        when(useCase.perform(any())).thenThrow(new RuntimeException("Error inesperado"));

        mockMvc.perform(get("/api/propietarios/" + UUID.randomUUID()))
                .andExpect(status().isInternalServerError());
    }
}
