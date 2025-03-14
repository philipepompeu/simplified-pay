package com.philipe.demo.application.services;

import java.util.List;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;


import com.philipe.demo.application.dto.NotificationDto;
import com.philipe.demo.domains.enums.NotificationStatus;
import com.philipe.demo.domains.model.NotificationEntity;
import com.philipe.demo.domains.repository.NotificationRepository;
import com.philipe.demo.infra.external.ExternalNotificationService;

@Service
public class NotificationProcessorService {   

    private final NotificationRepository notificationRepository;
    private final ExternalNotificationService externalNotificationService;

    public NotificationProcessorService(NotificationRepository notificationRepository, ExternalNotificationService externalNotificationService) {
        this.notificationRepository = notificationRepository;
        this.externalNotificationService = externalNotificationService;
    }

    @Scheduled(fixedDelay = 90000)
    public void processPendingNotifications(){

        
        List<NotificationEntity> pending = notificationRepository.findByStatusIn(List.of(NotificationStatus.PENDING, NotificationStatus.FAILED));

        
        for(NotificationEntity notification : (Iterable<NotificationEntity>) pending.stream()::iterator){
            
            NotificationStatus currentStatus = notification.getStatus();

            NotificationStatus newStatus = externalNotificationService.send(new NotificationDto(notification.getEmail(), notification.getMessage()));

            notification.setStatus(newStatus);                   
            
            
            System.out.println(String.format("Notification status changed from %s to %s", currentStatus.name(), newStatus.name()));
            
        }

        this.notificationRepository.saveAllAndFlush(pending);

        
    }
}
