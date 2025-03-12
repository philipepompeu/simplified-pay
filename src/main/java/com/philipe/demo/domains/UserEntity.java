package com.philipe.demo.domains;

import com.philipe.demo.enums.TypeOfClient;
import com.philipe.demo.enums.TypeOfUser;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(unique = true)
    private String cpfOrCnpj;
    
    private TypeOfUser typeOfUser;
    
    private TypeOfClient typeOfClient;

    private String password;

    
}
