package com.philipe.demo.domains;

import com.philipe.demo.enums.TypeOfClient;
import com.philipe.demo.enums.TypeOfUser;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;

    private String fullName;

    
    private String email;
    
    
    private String cpfOrCnpj;
    
    private TypeOfUser typeOfUser;
    
    private TypeOfClient typeOfClient;

    private String password;
}
