package gov.justucuman.seguridad_barrial.infrastructure.input;

import gov.justucuman.seguridad_barrial.infrastructure.output.EventoSeguridadStreamBroker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EventosSeguridadGetAdapter.class)
class EventosSeguridadGetAdapterTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventoSeguridadStreamBroker streamBroker;

    @Test
    void shouldReturnEventStream_whenSubscribingToNotifications() throws Exception {
        when(streamBroker.subscribe()).thenReturn(new SseEmitter(0L));

        mockMvc.perform(get("/api/eventos-seguridad/stream"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM));

        verify(streamBroker).subscribe();
    }
}
