package com.philipe.demo.infra.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.philipe.demo.application.dto.NotificationDto;

@FeignClient(name = "externalNotificationService", url = "https://util.devi.tools/api/v1")
public interface ExternalNotificationClient {
    
    @PostMapping("/notify")
    ResponseEntity<Void> send(@RequestBody NotificationDto dto);
}
