/*
 * Copyright (c) 2024.
 * Hadjersi Mohamed
 */

package fr.cp.subscriber.services;


import fr.cp.subscriber.dto.SubscriberReq;
import fr.cp.subscriber.dto.SubscriberResp;
import fr.cp.subscriber.entities.Subscriber;
import fr.cp.subscriber.exception.SubscriberAlreadyExistsException;
import fr.cp.subscriber.mapper.Mapper;
import fr.cp.subscriber.mapper.MapperPatten;
import fr.cp.subscriber.repositories.SubscriberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriberServiceImpl implements SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private Mapper<Subscriber, SubscriberReq, SubscriberResp> mapper;

    @Override
    public SubscriberResp create(SubscriberReq subscriber) {
        if (subscriberRepository.existsByFNameAndLNameAndMail(subscriber.fName(), subscriber.lName(), subscriber.mail())) {
            throw new SubscriberAlreadyExistsException("Subscriber with the same name and email already exists");
        }
        subscriber = subscriber.activateOrDesactivateSubscriber(true);
        return mapper.mapToResponse(subscriberRepository.save(
                mapper.mapToEntity(subscriber, MapperPatten.CREATE))
        );
    }

    @Override
    public SubscriberResp update(SubscriberReq subscriber) {

        final var id = subscriber.id();
        if (id == null) {
            throw new IllegalArgumentException("Cannot update an entity without an ID");
        }
        return mapper.mapToResponse(subscriberRepository.save(mapper.mapToEntity(subscriber, MapperPatten.UPDATE)));
    }
    @Override
    public void deactivate(Long id) {
        var subscriber = subscriberRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subscriber with ID " + id + " not found"));
        subscriber.setActive(false);
        subscriberRepository.save(subscriber);
    }

    @Override

    public List<SubscriberResp> searchSubscribers(String fName, String lName, String mail, String phone, Boolean isActive) {
        List<Subscriber> subscribers = subscriberRepository.search(fName, lName, mail, phone, isActive);
        return subscribers.stream().map(mapper::mapToResponse).toList();
    }
}
