package com.philipe.demo.presentation.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.philipe.demo.application.dto.TransferDto;
import com.philipe.demo.application.dto.UserDto;
import com.philipe.demo.domains.enums.ClientType;
import com.philipe.demo.domains.enums.UserType;
import com.philipe.demo.infra.external.ExternalAuthorizationService;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransferControllerTest {

    private UserDto payer;
    private UserDto payee;
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @MockitoBean
    private ExternalAuthorizationService authorizationService;
    
    @BeforeEach
    public void BeforeEach(){

        Random random = new Random();       

        payer = new UserDto();
        payer.setFullName("payer");
        payer.setClientType(ClientType.NATURAL_PERSON);
        payer.setLegalIdentifier("9246249208"+ String.valueOf(random.nextInt()));
        payer.setEmail(String.valueOf(random.nextInt())+"admin@provider.com");
        payer.setBalance(new BigDecimal(1000));
        payer.setUserType(UserType.SIMPLE_USER);
        payer.setPassword("pwd");
        
        payer = postUser(payer).getBody();
        
        payee = new UserDto();
        payee.setFullName("payee");
        payee.setClientType(ClientType.NATURAL_PERSON);
        payee.setLegalIdentifier("9246249208"+ String.valueOf(random.nextInt()));
        payee.setEmail(String.valueOf(random.nextInt())+"admin@provider.com");
        payee.setBalance(new BigDecimal(1000));
        payee.setUserType(UserType.SIMPLE_USER);
        payee.setPassword("pwd");
        
        payee = postUser(payee).getBody();

    }


    private ResponseEntity<UserDto> postUser(UserDto dto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(
            "/users",
            HttpMethod.POST,
            new HttpEntity<>(dto, headers),
            UserDto.class );   
    }

    private ResponseEntity<TransferDto> post(TransferDto dto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(
            "/transfer",
            HttpMethod.POST,
            new HttpEntity<>(dto, headers),
            TransferDto.class );   
    }
    
    private ResponseEntity<String> postButFail(TransferDto dto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(
            "/transfer",
            HttpMethod.POST,
            new HttpEntity<>(dto, headers),
            String.class );   
    }

    @Test
    void shouldTransferMoneySuccessfully(){
        // Simula que o serviço externo sempre autoriza a transação
        Mockito.when(authorizationService.authorize(Mockito.any(TransferDto.class))).thenReturn(true);

        
        TransferDto transfer = new TransferDto(BigDecimal.valueOf(10), payer.getId(), payee.getId(), LocalDateTime.now());

        ResponseEntity<TransferDto> response = this.post(transfer);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getValue()).isEqualTo(BigDecimal.valueOf(10));
    }
    
    @Test
    void shouldFailBecauseExternalAuthorizationServiceReturnedFalse(){        
        Mockito.when(authorizationService.authorize(Mockito.any(TransferDto.class))).thenReturn(false);
        
        TransferDto transfer = new TransferDto(BigDecimal.valueOf(10), payer.getId(), payee.getId(), LocalDateTime.now());

        ResponseEntity<String> response = this.postButFail(transfer);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("External service did not authorize the transaction");
    }
    
    @Test
    void shouldFailBecauseExceedUserBalance(){
        // Simula que o serviço externo sempre autoriza a transação
        Mockito.when(authorizationService.authorize(Mockito.any(TransferDto.class))).thenReturn(true);
        
        
        TransferDto transfer = new TransferDto( payer.getBalance().add(BigDecimal.valueOf(10)),//soma 10 ao saldo atual do pagador
                                                payer.getId(), payee.getId(), LocalDateTime.now());

        ResponseEntity<String> response = this.postButFail(transfer);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("exceed user balance");
    }
    
    @Test
    void shouldFailBecauseMerchantsCantBeThePayer(){
        // Simula que o serviço externo sempre autoriza a transação
        Mockito.when(authorizationService.authorize(Mockito.any(TransferDto.class))).thenReturn(true);

        UserDto merchant = new UserDto();
        merchant.setFullName("Im a Merchant");
        merchant.setClientType(ClientType.LEGAL_ENTITY);
        merchant.setLegalIdentifier("96399505000140");
        merchant.setEmail("merchant@provider.com");
        merchant.setBalance(new BigDecimal(1000));
        merchant.setUserType(UserType.MERCHANT);
        merchant.setPassword("pwd");        
        merchant = postUser(merchant).getBody();        
        
        TransferDto transfer = new TransferDto( BigDecimal.valueOf(10), merchant.getId(), payee.getId(), LocalDateTime.now());

        ResponseEntity<String> response = this.postButFail(transfer);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).contains("Merchant can only receive money.");
    }
    
}
