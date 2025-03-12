package com.philipe.demo.services;

import com.philipe.demo.domains.User;
import com.philipe.demo.domains.UserEntity;
import com.philipe.demo.domains.UserEntityRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    private final UserEntityRepository repository;

    public UserService(UserEntityRepository repository){
        this.repository = repository;
    }

    public void addUser(User user){        
        repository.saveAndFlush((this.fromUserDto(user)));
    }

    private UserEntity fromUserDto(User user){

        UserEntity entity = new UserEntity();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String hashedPassword = encoder.encode(user.getPassword());
        
        entity.setEmail(user.getEmail());
        entity.setCpfOrCnpj(user.getCpfOrCnpj());
        entity.setFullName(user.getFullName());
        entity.setTypeOfClient(user.getTypeOfClient());
        entity.setTypeOfUser(user.getTypeOfUser());
        entity.setPassword(hashedPassword);

        return entity;
    }
}
