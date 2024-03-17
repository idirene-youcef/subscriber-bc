package fr.cp.subscriber.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SubscriberAlreadyExistsException extends RuntimeException {

    public SubscriberAlreadyExistsException(String message) {
        super(message);
    }
}