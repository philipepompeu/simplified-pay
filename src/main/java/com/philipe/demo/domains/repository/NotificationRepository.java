package com.philipe.demo.domains.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.philipe.demo.domains.enums.NotificationStatus;
import com.philipe.demo.domains.model.NotificationEntity;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
    
    public List<NotificationEntity> findByStatusIn(List<NotificationStatus> status);

}
