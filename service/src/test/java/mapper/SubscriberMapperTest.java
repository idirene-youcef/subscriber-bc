package mapper;

import fr.cp.subscriber.dto.SubscriberReq;
import fr.cp.subscriber.dto.SubscriberResp;
import fr.cp.subscriber.entities.Subscriber;
import fr.cp.subscriber.mapper.MapperPatten;
import fr.cp.subscriber.mapper.SubscriberMapper;
import fr.cp.subscriber.repositories.SubscriberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubscriberMapperTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @InjectMocks
    private SubscriberMapper subscriberMapper;

    @Test
    void mapToEntity_createPattern_shouldReturnSubscriberEntity() {
        SubscriberReq request = getSubscriberRequest();

        Subscriber subscriber = subscriberMapper.mapToEntity(request, MapperPatten.CREATE);

        assertNotNull(subscriber);
        assertNull(subscriber.getId());
        assertEquals("test", subscriber.getFName());
        assertEquals("test", subscriber.getLName());
        assertEquals("test.test@example.com", subscriber.getMail());
        assertEquals("1234567890", subscriber.getPhone());
        assertTrue(subscriber.isActive());
        assertNull(subscriber.getCreationDate());
        assertNull(subscriber.getModificationDate());
        verifyNoInteractions(subscriberRepository);
    }

    @Test
    void mapToEntity_updatePattern_shouldReturnSubscriberEntity() {
        Long subscriberId = 1L;
        SubscriberReq request = getSubscriberRequest();
        Subscriber existingSubscriber = new Subscriber();
        existingSubscriber.setId(subscriberId);
        existingSubscriber.setFName("Existing");
        existingSubscriber.setLName("Subscriber");
        existingSubscriber.setMail("existing.subscriber@example.com");
        existingSubscriber.setPhone("0987654321");
        existingSubscriber.setActive(true);
        existingSubscriber.setCreationDate(LocalDateTime.now());
        existingSubscriber.setModificationDate(LocalDateTime.now());

        when(subscriberRepository.findById(anyLong())).thenReturn(Optional.of(existingSubscriber));

        Subscriber subscriber = subscriberMapper.mapToEntity(request, MapperPatten.UPDATE);

        assertNotNull(subscriber);
        assertEquals(subscriberId, subscriber.getId());
        assertEquals("test", subscriber.getFName());
        assertEquals("test", subscriber.getLName());
        assertEquals("test.test@example.com", subscriber.getMail());
        assertEquals("1234567890", subscriber.getPhone());
        assertTrue(subscriber.isActive());
        assertNotNull(subscriber.getCreationDate());
        assertNotNull(subscriber.getModificationDate());

        verify(subscriberRepository).findById(subscriberId);
        verifyNoMoreInteractions(subscriberRepository);
    }

    @Test
    void mapToEntity_updatePattern_subscriberNotFound_shouldThrowEntityNotFoundException() {
        when(subscriberRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            subscriberMapper.mapToEntity(getSubscriberRequest(), MapperPatten.UPDATE);
        });

        verify(subscriberRepository).findById(1L);
        verifyNoMoreInteractions(subscriberRepository);
    }

    @Test
    void mapToResponse_shouldReturnSubscriberResp() {
        Subscriber subscriber = getSubscriberEntity();
        SubscriberResp subscriberResp = subscriberMapper.mapToResponse(subscriber);

        assertNotNull(subscriberResp);
        assertEquals(1L, subscriberResp.id());
        assertEquals("test", subscriberResp.fName());
        assertEquals("test", subscriberResp.lName());
        assertEquals("test.test@example.com", subscriberResp.mail());
        assertEquals("1234567890", subscriberResp.phone());
        assertTrue(subscriberResp.isActive());
        assertEquals(creationDate, subscriberResp.creationDate());
        assertEquals(modificationDate, subscriberResp.modificationDate());
        verifyNoInteractions(subscriberRepository);
    }

    public LocalDateTime creationDate = LocalDateTime.of(2022, 3, 14, 10, 30);
    public LocalDateTime modificationDate = LocalDateTime.of(2022, 3, 15, 15, 45);

    private Subscriber getSubscriberEntity() {
        return new Subscriber(1L, "test", "test", "test.test@example.com", "1234567890", true, creationDate, modificationDate);
    }

    private SubscriberReq getSubscriberRequest() {
        return new SubscriberReq(1L, "test", "test", "test.test@example.com", "1234567890", true);
    }

    private SubscriberResp getSubscriberResp() {

        return new SubscriberResp(1L, "test", "test", "test.test@example.com",
                "1234567890", true, creationDate, modificationDate);
    }
}
