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
import com.philipe.demo.application.dto.UserDtoBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private UserDtoBuilder userBuilder = new UserDtoBuilder();

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
        
        UserDto naturalPersonUser = userBuilder.naturalPerson()
                                                .simpleUser()
                                                .fullName("Admin")
                                                .legalIdentifier("92462492089")
                                                .email("admin@provider.com")
                                                .withBalanceOf(1000)
                                                .password("pwd")
                                                .build();      
        
        ResponseEntity<UserDto> response = this.post(naturalPersonUser);       
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();

    }

    @Test
    void shouldCreateNewLegalEntityUser(){ 
        UserDto legalEntityUser = userBuilder.legalEntity()
                                                .simpleUser()
                                                .fullName("Admin")
                                                .legalIdentifier("15984376000155")
                                                .email("entity@provider.com")
                                                .withBalanceOf(1000)
                                                .password("pwd")
                                                .build();

        ResponseEntity<UserDto> response = this.post(legalEntityUser);       
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isNotNull();        

    }

    @Test
    void shouldNotAllowedDuplicatedEmails(){
       
        UserDto firstUser = userBuilder.naturalPerson()
                                        .simpleUser()
                                        .fullName("Admin")
                                        .legalIdentifier("12462492089")
                                        .email("admin1@provider.com")
                                        .withBalanceOf(1000)
                                        .password("pwd")
                                        .build();        

        assertThat(this.post(firstUser).getStatusCode()).isEqualTo(HttpStatus.OK);        

        UserDto secondUser =userBuilder.naturalPerson()
                                        .simpleUser()
                                        .fullName("Admin")
                                        .legalIdentifier("12462492089")
                                        .email("admin1@provider.com")
                                        .withBalanceOf(1000)
                                        .password("pwd")
                                        .build();     

        ResponseEntity<String> response = this.postExpectError(secondUser); 

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertTrue(response.getBody().contains("Legal Id or email already exists")) ;


    }
    
}
