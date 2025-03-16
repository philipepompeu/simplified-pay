package com.philipe.demo.application.services;

import com.philipe.demo.application.dto.UserDto;
import com.philipe.demo.application.mapper.UserMapper;
import com.philipe.demo.domains.enums.UserType;
import com.philipe.demo.domains.model.UserEntity;
import com.philipe.demo.domains.repository.UserEntityRepository;
import com.philipe.demo.presentation.exception.RequestValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserEntityRepository repository;
    private final List<EventListener> observers = new ArrayList<>();

    public UserService(UserEntityRepository repository, NotificationListener notificationListener){
        this.repository = repository;
        observers.add(notificationListener);
    }

    public boolean canTheUserBeCreated(UserDto user){

        Optional<UserEntity> entity = this.repository.findByLegalIdentifierOrEmail(user.getLegalIdentifier(), user.getEmail());

        if (entity.isPresent()) {
           return false;
        }
        
        return true;
    }

    public UserDto addUser(UserDto user) throws RequestValidationException{        

        if (!this.canTheUserBeCreated(user)) {
            throw new RequestValidationException(String.format("Legal Id or email already exists [ %s  - %s ]", user.getLegalIdentifier(), user.getEmail()));
        }

        user.setId(null);
        return UserMapper.toDto(repository.saveAndFlush((UserMapper.toEntity(user))));        
        
    }

    public void withdrawFromUser(BigDecimal value, UserEntity user) throws Exception{

        if (user.getUserType().equals(UserType.MERCHANT)) {
            throw  new RequestValidationException("Merchant can only receive money."); //Lojistas **só recebem** transferências, não enviam dinheiro para ninguém
        }

        if (!(user.getBalance().compareTo(value) >= 0)) {//Validar se o usuário tem saldo antes da transferências;
            throw new RequestValidationException("exceed user balance");
        }
        
        BigDecimal newBalance = user.getBalance().subtract(value);
        user.setBalance(newBalance);    
        
        System.out.println(String.format("withdrawFromUser id [ %d ]", user.getId()));
        
        repository.save(user);
    }

    public void depositToUser(BigDecimal value, UserEntity user){
        BigDecimal newBalance = user.getBalance().add(value);
        
        user.setBalance(newBalance);
        
        System.out.println(String.format("depositToUser id [ %d ] -> new balance %s ", user.getId(), newBalance.toPlainString()));

        repository.save(user);

        // Notifica todos os observadores sobre o evento de depósito
        for (EventListener observer : observers) {
            observer.onDeposit(value, user);
        }
    }

    //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();          
    //String hashedPassword = encoder.encode(user.getPassword());        
    //entity.setPassword(hashedPassword);    
}
