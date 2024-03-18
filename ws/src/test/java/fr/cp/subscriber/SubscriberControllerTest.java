package fr.cp.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.cp.subscriber.controllers.SubscriberController;
import fr.cp.subscriber.dto.SubscriberReq;
import fr.cp.subscriber.dto.SubscriberResp;
import fr.cp.subscriber.services.SubscriberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(MockitoJUnitRunner.class)
public class SubscriberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private SubscriberService subscriberService;


    @InjectMocks
    private SubscriberController subscriberController;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subscriberController).build();
    }

    @Test
    public void testCreateSubscriber_shouldCreateSubscriber() throws Exception {
        SubscriberReq subscriberReq = getSubscriberRequest();
        when(subscriberService.create(any(SubscriberReq.class))).thenReturn(getSubscriberResp());
        mockMvc.perform(post("/api/v1/subscribers/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(subscriberReq)))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("/api/v1/subscribers/1")));
        verify(subscriberService).create(subscriberReq);
        verifyNoMoreInteractions(subscriberService);
    }

    @Test
    public void testUpdateSubscriber_shouldUpdateSubscriber() throws Exception {
        SubscriberReq subscriberReq = getSubscriberRequest();
        when(subscriberService.update(any(SubscriberReq.class))).thenReturn(getSubscriberResp());

        mockMvc.perform(put("/api/v1/subscribers/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(subscriberReq)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(subscriberService).update(subscriberReq);
        verifyNoMoreInteractions(subscriberService);
    }

    @Test
    public void testSearchSubscriber_shouldReturnSubscribers() throws Exception {
        String fName = "test";
        String lName = "test";
        String mail = "test.test@example.com";
        String phone = "1234567890";
        Boolean isActive = true;

        SubscriberResp subscriberResp = getSubscriberResp();

        when(subscriberService.searchSubscribers(fName, lName, mail, phone, isActive))
                .thenReturn(Collections.singletonList(subscriberResp));

        mockMvc.perform(get("/api/v1/subscribers/search")
                        .param("fName", fName)
                        .param("lName", lName)
                        .param("mail", mail)
                        .param("phone", phone)
                        .param("isActive", isActive.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1));

        verify(subscriberService).searchSubscribers(fName, lName, mail, phone, isActive);
        verifyNoMoreInteractions(subscriberService);
    }

    @Test
    public void testDeactivateSubscriber_shouldDeactivateSubscriber() throws Exception {
        Long subscriberId = 1L;

        mockMvc.perform(delete("/api/v1/subscribers/{id}", subscriberId))
                .andExpect(status().isOk())
                .andExpect(content().string("Subscriber deactivated"));
        verify(subscriberService).deactivate(subscriberId);
        verifyNoMoreInteractions(subscriberService);
    }

    private static SubscriberResp getSubscriberResp() {
        LocalDateTime creationDate = LocalDateTime.of(2022, 3, 14, 10, 30);
        LocalDateTime modificationDate = LocalDateTime.of(2022, 3, 15, 15, 45);

        return new SubscriberResp(1L, "test", "test", "test.test@example.com",
                "123456789", true, creationDate, modificationDate);
    }

    private SubscriberReq getSubscriberRequest() {
        return new SubscriberReq(null, "test", "test", "test.test@example.com", "1234567890", true);
    }

}
