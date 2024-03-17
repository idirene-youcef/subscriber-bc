/*
 * Copyright (c) 2024.
 * Hadjersi Mohamed
 */
package fr.cp.subscriber.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "subscriber")
@EntityListeners(AuditingEntityListener.class)
public class Subscriber {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String fName;
  private String lName;
  private String mail;
  private String phone;
  private boolean isActive;
  @CreatedDate
  private LocalDateTime creationDate;
  @LastModifiedDate
  private LocalDateTime modificationDate;


  public Subscriber(long l, String john, String doe, String mail, String number, boolean b) {
  }
}
