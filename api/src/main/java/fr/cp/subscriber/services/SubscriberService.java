/*
 * Copyright (c) 2024.
 * Hadjersi Mohamed
 */

package fr.cp.subscriber.services;


import fr.cp.subscriber.dto.SubscriberReq;
import fr.cp.subscriber.dto.SubscriberResp;

import java.util.List;

public interface SubscriberService {
    /**
     * Creates a new subscriber.
     *
     * @param request The request containing the details of the new subscriber to be created.
     * @return The response containing the details of the newly created subscriber.
     */
    public SubscriberResp create(SubscriberReq request);
    /**
     * Updates the details of a subscriber.
     *
     * @param request The request containing the updated subscriber details.
     * @return The response containing the updated subscriber details.
     */
    public SubscriberResp update(SubscriberReq request);
    /**
     * Deactivates a subscriber with the specified ID.
     *
     * @param id The ID of the subscriber to deactivate.
     */
    public void deactivate(Long id);

    /**
     * Searches for subscribers based on the provided criteria.
     *
     * @param fName    The first name of the subscriber. Optional.
     * @param lName    The last name of the subscriber. Optional.
     * @param mail     The email address of the subscriber. Optional.
     * @param phone    The phone number of the subscriber. Optional.
     * @param isActive The status of the subscriber. Optional.
     * @return List<SubscriberResp>
     **/
    List<SubscriberResp> searchSubscribers(String fName, String lName, String mail, String phone, Boolean isActive);
}
