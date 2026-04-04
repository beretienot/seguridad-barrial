package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.application.EventoSeguridadCreator;
import gov.justucuman.seguridad_barrial.application.EventoSeguridadCreatorCommand;
import gov.justucuman.seguridad_barrial.application.EventoSeguridadCreatorResult;
import gov.justucuman.seguridad_barrial.domain.PropiedadNotFoundException;
import gov.justucuman.seguridad_barrial.infrastructure.input.dto.EventoSeguridadCreatorRequestMother;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.UUID;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventoSeguridadPostAdapter.class)
@Import(EventoSeguridadPostAdapterTest.MapperConfig.class)
class EventoSeguridadPostAdapterTest {

    @TestConfiguration
    static class MapperConfig {
        @Bean
        EventoSeguridadCreatorAdapterMapper eventoSeguridadCreatorAdapterMapper() {
            return Mappers.getMapper(EventoSeguridadCreatorAdapterMapper.class);
        }
    }

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventoSeguridadCreator useCase;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnCreatedWithNoBody_whenValidRequest() throws Exception {
        UUID propiedadId = UUID.randomUUID();
        var request = EventoSeguridadCreatorRequestMother.valid();

        when(useCase.perform(any())).thenReturn(
            EventoSeguridadCreatorResult.builder()
                .id(request.getId())
                .propiedadId(propiedadId)
                .tipo(request.getTipo())
                .descripcion(request.getDescripcion())
                .fecha(java.time.LocalDateTime.now().withNano(0))
                .latitud(new BigDecimal("-26.82411"))
                .longitud(new BigDecimal("-65.22265"))
                .build()
        );

        mockMvc.perform(post("/api/propiedades/{propiedadId}/eventos-seguridad", propiedadId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string(""));

        ArgumentCaptor<EventoSeguridadCreatorCommand> captor = ArgumentCaptor.forClass(EventoSeguridadCreatorCommand.class);
        verify(useCase).perform(captor.capture());
        EventoSeguridadCreatorCommand captured = captor.getValue();
        assertThat(captured.getId()).isEqualTo(request.getId());
        assertThat(captured.getPropiedadId()).isEqualTo(propiedadId);
        assertThat(captured.getTipo()).isEqualTo(request.getTipo());
        assertThat(captured.getDescripcion()).isEqualTo(request.getDescripcion());
    }

    @Test
    void shouldReturnBadRequest_whenTipoIsNull() throws Exception {
        UUID propiedadId = UUID.randomUUID();
        var request = EventoSeguridadCreatorRequestMother.withoutTipo();

        mockMvc.perform(post("/api/propiedades/{propiedadId}/eventos-seguridad", propiedadId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequest_whenIdIsNull() throws Exception {
        UUID propiedadId = UUID.randomUUID();
        var request = EventoSeguridadCreatorRequestMother.withoutId();

        mockMvc.perform(post("/api/propiedades/{propiedadId}/eventos-seguridad", propiedadId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnNotFound_whenUseCaseThrowsPropiedadNotFound() throws Exception {
        UUID propiedadId = UUID.randomUUID();
        var request = EventoSeguridadCreatorRequestMother.valid();

        doThrow(new PropiedadNotFoundException("Propiedad no encontrada"))
                .when(useCase)
                .perform(any());

        mockMvc.perform(post("/api/propiedades/{propiedadId}/eventos-seguridad", propiedadId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}
