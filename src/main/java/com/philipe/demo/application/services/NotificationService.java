package com.philipe.demo.application.services;


import org.springframework.stereotype.Service;

import com.philipe.demo.application.dto.NotificationDto;
import com.philipe.demo.domains.enums.NotificationStatus;
import com.philipe.demo.domains.model.NotificationEntity;
import com.philipe.demo.domains.repository.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public void addNotification(NotificationDto dto){

        NotificationEntity entity = new NotificationEntity(dto.email(),dto.message());
        entity.setStatus(NotificationStatus.PENDING);
        
        this.repository.saveAndFlush(entity);

    }
}
