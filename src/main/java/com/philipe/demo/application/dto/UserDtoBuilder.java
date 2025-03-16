package com.philipe.demo.application.dto;

import java.math.BigDecimal;

import com.philipe.demo.domains.enums.ClientType;
import com.philipe.demo.domains.enums.UserType;

public class UserDtoBuilder {

    private String fullName = "";
    private String email = "";    
    private String legalIdentifier = "";
    private UserType userType;
    private ClientType clientType;
    private String password = "";
    private BigDecimal balance;

    public UserDtoBuilder fullName(String fullName){
        this.fullName = fullName;
        return this;
    }
    public UserDtoBuilder email(String email){
        this.email = email;
        return this;
    }

    public UserDtoBuilder password(String password){
        this.password = password;
        return this;
    }
    
    public UserDtoBuilder legalIdentifier(String legalIdentifier){
        this.legalIdentifier = legalIdentifier;
        return this;
    }

    public UserDtoBuilder merchant(){
        this.userType = UserType.MERCHANT;
        return this;
    }
    
    public UserDtoBuilder simpleUser(){
        this.userType = UserType.SIMPLE_USER;
        return this;
    }
    
    public UserDtoBuilder naturalPerson(){
        this.clientType = ClientType.NATURAL_PERSON;
        return this;
    }
    
    public UserDtoBuilder legalEntity(){
        this.clientType = ClientType.LEGAL_ENTITY;
        return this;
    }

    public UserDtoBuilder withBalanceOf(long balance){
        this.balance = BigDecimal.valueOf(balance);
        return this;
    }

    public UserDto build(){
        UserDto dto = new UserDto();
        dto.setFullName(fullName);
        dto.setBalance(balance);
        dto.setClientType(clientType);
        dto.setEmail(email);
        dto.setLegalIdentifier(legalIdentifier);
        dto.setPassword(password);
        dto.setUserType(userType);
        return dto;
    }

}
