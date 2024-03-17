package fr.cp.subscriber.mapper;


import fr.cp.subscriber.dto.SubscriberReq;
import fr.cp.subscriber.dto.SubscriberResp;
import fr.cp.subscriber.entities.Subscriber;
import fr.cp.subscriber.repositories.SubscriberRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubscriberMapper implements Mapper<Subscriber, SubscriberReq, SubscriberResp> {

    @Autowired
    private SubscriberRepository subscriberRepository;

    @Override
    public Subscriber mapToEntity(SubscriberReq request, MapperPatten pattern) {
        Subscriber subscriber = null;
        if (MapperPatten.CREATE == pattern) {
            subscriber = new Subscriber();
        } else {
            subscriber = subscriberRepository.findById(request.id())
                    .orElseThrow(() -> new EntityNotFoundException("Subscriber with ID " + request.id() + " not found"));
        }
        subscriber.setLName(request.lName());
        subscriber.setFName(request.fName());
        subscriber.setPhone(request.phone());
        subscriber.setActive(request.isActive());
        subscriber.setMail(request.mail());
        return subscriber;
    }

    @Override
    public SubscriberResp mapToResponse(Subscriber entity) {

        return SubscriberResp.builder()
                .mail(entity.getMail())
                .id(entity.getId())
                .fName(entity.getFName())
                .phone(entity.getPhone())
                .isActive(entity.isActive())
                .creationDate(entity.getCreationDate())
                .modificationDate(entity.getModificationDate())
                .lName(entity.getLName())
                .build();
    }
}
