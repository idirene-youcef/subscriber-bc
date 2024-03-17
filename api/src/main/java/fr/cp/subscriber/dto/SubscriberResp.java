/*
 * Copyright (c) 2024.
 * Hadjersi Mohamed
 */

package fr.cp.subscriber.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record SubscriberResp(Long id,
                             String fName,
                             String lName,
                             String mail,
                             String phone,
                             boolean isActive,
                             @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime creationDate,
                             @JsonFormat(pattern = "dd/MM/yyyy HH:mm") LocalDateTime modificationDate) {
}
