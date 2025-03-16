package com.philipe.demo.presentation.controllers;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.philipe.demo.application.dto.UserDto;
import com.philipe.demo.domains.enums.ClientType;
import com.philipe.demo.domains.enums.UserType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<UserDto> post(UserDto dto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(
            "/users",
            HttpMethod.POST,
            new HttpEntity<>(dto, headers),
            UserDto.class );   
    }
    
    private ResponseEntity<String> postExpectError(UserDto dto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(
            "/users",
            HttpMethod.POST,
            new HttpEntity<>(dto, headers),
            String.class );   
    }

    @Test
    void shouldCreateNewNaturalPersonUser(){        

        UserDto naturalPersonUser = new UserDto();
        naturalPersonUser.setFullName("Admin");
        naturalPersonUser.setClientType(ClientType.NATURAL_PERSON);
        naturalPersonUser.setLegalIdentifier("92462492089");
        naturalPersonUser.setEmail("admin@provider.com");
        naturalPersonUser.setBalance(new BigDecimal(1000));
        naturalPersonUser.setUserType(UserType.SIMPLE_USER);
        naturalPersonUser.setPassword("pwd");       
        
        ResponseEntity<UserDto> response = this.post(naturalPersonUser);       
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();

    }

    @Test
    void shouldCreateNewLegalEntityUser(){        

        UserDto legalEntityUser = new UserDto();
        legalEntityUser.setFullName("Admin");
        legalEntityUser.setClientType(ClientType.LEGAL_ENTITY);
        legalEntityUser.setLegalIdentifier("15984376000155");
        legalEntityUser.setEmail("entity@provider.com");
        legalEntityUser.setBalance(new BigDecimal(1000));
        legalEntityUser.setUserType(UserType.SIMPLE_USER);
        legalEntityUser.setPassword("pwd");       
        
        ResponseEntity<UserDto> response = this.post(legalEntityUser);       
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();        

    }

    @Test
    void shouldNotAllowedDuplicatedEmails(){        

        UserDto firstUser = new UserDto();
        firstUser.setFullName("Admin");
        firstUser.setClientType(ClientType.NATURAL_PERSON);
        firstUser.setLegalIdentifier("12462492089");
        firstUser.setEmail("admin1@provider.com");
        firstUser.setBalance(new BigDecimal(1000));
        firstUser.setUserType(UserType.SIMPLE_USER);
        firstUser.setPassword("pwd");        

        assertThat(this.post(firstUser).getStatusCode()).isEqualTo(HttpStatus.OK);        

        UserDto secondUser = new UserDto();
        secondUser.setFullName("Admin2");
        secondUser.setClientType(ClientType.NATURAL_PERSON);
        secondUser.setLegalIdentifier("12462492087");
        secondUser.setEmail("admin1@provider.com");
        secondUser.setBalance(new BigDecimal(1000));
        secondUser.setUserType(UserType.SIMPLE_USER);
        secondUser.setPassword("pwd"); 

        ResponseEntity<String> response = this.postExpectError(secondUser); 

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertTrue(response.getBody().contains("Legal Id or email already exists")) ;


    }
    
}
