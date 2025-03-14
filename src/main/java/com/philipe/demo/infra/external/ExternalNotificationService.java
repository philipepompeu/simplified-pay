package com.philipe.demo.infra.external;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.philipe.demo.application.dto.NotificationDto;
import com.philipe.demo.domains.enums.NotificationStatus;

@Service
public class ExternalNotificationService {

    private static final String NOTIFY_URL = "https://util.devi.tools/api/v1/notify";
    private final RestTemplate restTemplate = new RestTemplate();


    public NotificationStatus send(NotificationDto dto){
        NotificationStatus status;
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(NOTIFY_URL, dto, String.class);
            
            if (response.getStatusCode().is2xxSuccessful()) {
                
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
