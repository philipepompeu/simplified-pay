package com.philipe.demo.application.services;

import com.philipe.demo.application.dto.UserDto;
import com.philipe.demo.application.mapper.UserMapper;
import com.philipe.demo.domains.enums.UserType;
import com.philipe.demo.domains.model.UserEntity;
import com.philipe.demo.domains.repository.UserEntityRepository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserEntityRepository repository;

    public UserService(UserEntityRepository repository){
        this.repository = repository;
    }

    public boolean canTheUserBeCreated(UserDto user){

        Optional<UserEntity> entity = this.repository.findByLegalIdentifierOrEmail(user.getLegalIdentifier(), user.getEmail());

        if (entity.isPresent()) {
           return false;
        }
        
        return true;
    }

    public UserDto addUser(UserDto user){   
        

        if (this.canTheUserBeCreated(user)) {
            user.setId(null);
    
            return UserMapper.toDto(repository.saveAndFlush((UserMapper.toEntity(user))));      
            
        }else{
            throw new DataIntegrityViolationException(String.format("Legal Id or email already exists [ %s  - %s ]", user.getLegalIdentifier(), user.getEmail()));
        }
        
    }

    public void withdrawFromUser(BigDecimal value, UserEntity user) throws Exception{

        if (user.getUserType().equals(UserType.MERCHANT)) {
            throw  new Exception("Merchant can only receive money."); //Lojistas **só recebem** transferências, não enviam dinheiro para ninguém;
        }

        if (user.getBalance().compareTo(value) >= 0) {//Validar se o usuário tem saldo antes da transferência;
            BigDecimal newBalance = user.getBalance().subtract(value);
            user.setBalance(newBalance);    
            
            System.out.println(String.format("withdrawFromUser id [ %d ]", user.getId()));
            
            repository.save(user);
        }else{
            throw new Exception("exceed user balance");
        }
    }

    public void depositToUser(BigDecimal value, UserEntity user){
        BigDecimal newBalance = user.getBalance().add(value);
        
        user.setBalance(newBalance);
        
        System.out.println(String.format("depositToUser id [ %d ] -> new balance %s ", user.getId(), newBalance.toPlainString()));

        repository.save(user);
    }

    //BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();          
    //String hashedPassword = encoder.encode(user.getPassword());        
    //entity.setPassword(hashedPassword);    
}
