package com.philipe.demo.application.services;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

import com.philipe.demo.application.dto.NotificationDto;
import com.philipe.demo.domains.model.UserEntity;

@Component
public class NotificationListener implements EventListener {

    private final NotificationService notificationService;

    public NotificationListener(final NotificationService service){
        this.notificationService = service;
    }

    @Override
    public void onDeposit(final BigDecimal value, final UserEntity user) {
        
        String message = String.format("You received %s", value.toString());        
        notificationService.addNotification(new NotificationDto(user.getEmail(), message) );        
    }

}
