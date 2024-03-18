
package fr.cp.subscriber.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
@Builder
public record SubscriberReq(@Null(groups = CreateValidation.class) Long id,
                            @NotBlank(message = "First name is mandatory") String fName,
                            @NotBlank(message = "Last name is mandatory") String lName,
                            @Email(message = "Invalid email address") String mail,
                            @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits") String phone,
                            boolean isActive){
    public SubscriberReq activateOrDesactivateSubscriber(boolean isActive) {
        return new SubscriberReq(id(), fName(), lName(), mail(), phone(), isActive);
    }

    public interface CreateValidation {}

}

