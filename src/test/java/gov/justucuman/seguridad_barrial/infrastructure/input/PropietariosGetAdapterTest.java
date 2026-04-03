package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.PropietarioByIdFinderResult;
import gov.justucuman.seguridad_barrial.application.PropietariosFinder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PropietariosGetAdapter.class)
@Import(PropietariosFinderAdapterMapperImpl.class)
class PropietariosGetAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PropietariosFinder useCase;

    @Test
    void shouldReturnOkWithPropietarios_whenThereAreRecords() throws Exception {
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        List<PropietarioByIdFinderResult> results = List.of(
                PropietarioByIdFinderResult.builder()
                        .id(id1).nombre("Carlos").apellido("Gomez")
                        .dni("28456123").direccion("Av. San Martin 450")
                        .telefono("3814501234").email("carlos@mail.com")
                        .build(),
                PropietarioByIdFinderResult.builder()
                        .id(id2).nombre("Maria").apellido("Rodriguez")
                        .dni("33789456").direccion("Calle Rivadavia 123")
                        .telefono(null).email(null)
                        .build()
        );
        when(useCase.perform()).thenReturn(results);

        mockMvc.perform(get("/api/propietarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(id1.toString()))
                .andExpect(jsonPath("$[0].nombre").value("Carlos"))
                .andExpect(jsonPath("$[1].id").value(id2.toString()))
                .andExpect(jsonPath("$[1].nombre").value("Maria"));

        verify(useCase).perform();
    }

    @Test
    void shouldReturnEmptyList_whenNoPropietariosExist() throws Exception {
        when(useCase.perform()).thenReturn(List.of());

        mockMvc.perform(get("/api/propietarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void shouldReturnInternalServerError_whenUseCaseThrowsUnexpectedException() throws Exception {
        when(useCase.perform()).thenThrow(new RuntimeException("Error inesperado"));

        mockMvc.perform(get("/api/propietarios"))
                .andExpect(status().isInternalServerError());
    }
}
