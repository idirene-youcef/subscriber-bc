package service;

import fr.cp.subscriber.dto.SubscriberReq;
import fr.cp.subscriber.dto.SubscriberResp;
import fr.cp.subscriber.entities.Subscriber;
import fr.cp.subscriber.exception.SubscriberAlreadyExistsException;
import fr.cp.subscriber.mapper.Mapper;
import fr.cp.subscriber.mapper.MapperPatten;
import fr.cp.subscriber.repositories.SubscriberRepository;
import fr.cp.subscriber.services.SubscriberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SubscriberServiceImplTest {

    @Mock
    private SubscriberRepository subscriberRepository;

    @Mock
    private Mapper<Subscriber, SubscriberReq, SubscriberResp> mapper;

    @InjectMocks
    private SubscriberServiceImpl subscriberService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSubscriber_ShouldCreateSubscriberWhenIttestsNotExist() {
        SubscriberReq request = getSubscriberRequest();
        Subscriber entity = getSubscriberEntity();
        SubscriberResp expectedResponse = getSubscriberResp();
        when(subscriberRepository.existsByFNameAndLNameAndMail(anyString(), anyString(), anyString())).thenReturn(false);
        when(mapper.mapToEntity(request, MapperPatten.CREATE)).thenReturn(entity);
        when(subscriberRepository.save(entity)).thenReturn(entity);
        when(mapper.mapToResponse(entity)).thenReturn(expectedResponse);

        SubscriberResp actualResponse = subscriberService.create(request);

        assertEquals(expectedResponse, actualResponse);
        verify(subscriberRepository).save(entity);
        verify(subscriberRepository).existsByFNameAndLNameAndMail("test", "test", "test.test@example.com");
        verifyNoMoreInteractions(subscriberRepository);
    }


    @Test
    void testCreateSubscriber_ShouldNotCreateSubscriberWhenAlreadyExists() {
        SubscriberReq request = getSubscriberRequest();

        when(subscriberRepository.existsByFNameAndLNameAndMail(anyString(), anyString(), anyString())).thenReturn(true);

        assertThrows(SubscriberAlreadyExistsException.class, () -> {
            subscriberService.create(request);
        });

        verify(subscriberRepository).existsByFNameAndLNameAndMail("test", "test", "test.test@example.com");
        verifyNoMoreInteractions(subscriberRepository);
    }

    @Test
    void testUpdateSubscriber_ShouldUpdateSubscriberWhenExist() {
        SubscriberReq request = getSubscriberRequest();
        Subscriber entity = getSubscriberEntity();
        SubscriberResp expectedResponse = getSubscriberResp();

        when(mapper.mapToEntity(any(SubscriberReq.class), any(MapperPatten.class))).thenReturn(entity);
        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(entity);
        when(mapper.mapToResponse(any(Subscriber.class))).thenReturn(expectedResponse);

        SubscriberResp actualResponse = subscriberService.update(request);

        assertEquals(expectedResponse, actualResponse);
        verify(subscriberRepository).save(entity);
        verify(mapper).mapToResponse(entity);
        verify(mapper).mapToEntity(request, MapperPatten.UPDATE);
        verifyNoMoreInteractions(subscriberRepository);
    }

    @Test
    void testUpdateSubscriber_ShouldThrowExceptionWhenIdIsNull() {
        SubscriberReq request = new SubscriberReq(null, "test", "test", "test.test@example.com", "123456789", true);;

        assertThrows(IllegalArgumentException.class, () -> {
            subscriberService.update(request);
        });
        verifyNoInteractions(subscriberRepository);
    }

    @Test
    void testDeactivateSubscriber_ShouldDeactivateSubscriberWhenExists() {
        Long id = 1L;
        Subscriber entity = getSubscriberEntity();

        when(subscriberRepository.findById(anyLong())).thenReturn(Optional.of(entity));
        when(subscriberRepository.save(any(Subscriber.class))).thenReturn(entity);

        subscriberService.deactivate(id);

        assertFalse(entity.isActive());
        verify(subscriberRepository).save(entity);
        verify(subscriberRepository).findById(id);
        verifyNoMoreInteractions(subscriberRepository);

    }

    @Test
    void testDeactivateSubscriber_ShouldThrowEntityNotFoundExceptionWhenSubscribertestsNotExist() {
        Long id = 1L;

        when(subscriberRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            subscriberService.deactivate(id);
        });
        verify(subscriberRepository).findById(id);
        verifyNoMoreInteractions(subscriberRepository);
    }

    private Subscriber getSubscriberEntity() {
        return new Subscriber(1L, "test", "test", "test.test@example.com", "123456789", true);
    }

    private SubscriberReq getSubscriberRequest() {
        return new SubscriberReq(1L, "test", "test", "test.test@example.com", "123456789", true);
    }
    private static SubscriberResp getSubscriberResp() {
        LocalDateTime creationDate = LocalDateTime.of(2022, 3, 14, 10, 30);
        LocalDateTime modificationDate = LocalDateTime.of(2022, 3, 15, 15, 45);

        return new SubscriberResp(1L, "test", "test", "test.test@example.com",
                "123456789", true, creationDate, modificationDate);
    }

}

