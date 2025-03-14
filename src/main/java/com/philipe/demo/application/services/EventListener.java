package com.philipe.demo.application.services;

import java.math.BigDecimal;

import com.philipe.demo.domains.model.UserEntity;

public interface EventListener {

    void onDeposit(BigDecimal value, UserEntity user);
}
