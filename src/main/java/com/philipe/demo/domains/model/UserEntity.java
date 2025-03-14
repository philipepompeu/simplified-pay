package com.philipe.demo.domains.model;

import java.math.BigDecimal;

import com.philipe.demo.domains.enums.ClientType;
import com.philipe.demo.domains.enums.UserType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "USERS")
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
    private String legalIdentifier;
    
    private UserType userType;
    
    private ClientType clientType;

    private String password;

    private BigDecimal balance;

    
}
