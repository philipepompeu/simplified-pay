package com.philipe.demo.infra.external;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.philipe.demo.application.dto.NotificationDto;
import com.philipe.demo.domains.enums.NotificationStatus;

@Service
public class ExternalNotificationService {
    private final ExternalNotificationClient externalNotificationClient;

    public ExternalNotificationService(ExternalNotificationClient externalNotificationClient){
        this.externalNotificationClient = externalNotificationClient;
    }


    public NotificationStatus send(NotificationDto dto){
        NotificationStatus status;
        try {            
            if (externalNotificationClient.send(dto).getStatusCode().is2xxSuccessful()) {                
                status = NotificationStatus.SENT;
            } else {                
                status = NotificationStatus.FAILED;
            }
            
        } catch (Exception e) {
            status = NotificationStatus.FAILED;            
        }
        
        return status;
    }

}
