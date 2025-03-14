package com.philipe.demo.domains.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.philipe.demo.domains.enums.NotificationStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="NOTIFICATIONS")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String email;
    
    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created_at;

    public NotificationEntity(String email, String message){
        this.email = email;
        this.message = message;
    }
}
