package fr.cp.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cp.subscriber.controllers.SubscriberController;
import fr.cp.subscriber.dto.SubscriberReq;
import fr.cp.subscriber.dto.SubscriberResp;
import fr.cp.subscriber.services.SubscriberService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

//@WebMvcTest(controllers = SubscriberController.class)
public class SubscriberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubscriberService subscriberService;


    @InjectMocks
    private SubscriberController subscriberController;


    @Test
    public void testCreateSubscriber_shouldCreateSubscriber() throws Exception {

    }

    @Test
    public void testSearchSubscriber_shouldReturnSubscriber() throws Exception {
        List<SubscriberResp> subscribers = Collections.singletonList(getSubscriberResp());

        // Mock service method
        Mockito.when(subscriberService.searchSubscribers(any(), any(), any(), any(), any())).thenReturn(subscribers);

        // Perform GET request
        mockMvc.perform(get("/api/v1/subscribers/search")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static SubscriberResp getSubscriberResp() {
        LocalDateTime creationDate = LocalDateTime.of(2022, 3, 14, 10, 30);
        LocalDateTime modificationDate = LocalDateTime.of(2022, 3, 15, 15, 45);

        return new SubscriberResp(1L, "test", "test", "test.test@example.com",
                "123456789", true, creationDate, modificationDate);
    }

}
