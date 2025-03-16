package com.philipe.demo.infra.external;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.philipe.demo.application.dto.TransferDto;

@Service
public class ExternalAuthorizationService {
    private static final String AUTHORIZE_URL = "https://util.devi.tools/api/v2/authorize";
    private final RestTemplate restTemplate = new RestTemplate();
    
    
    public boolean authorize(TransferDto dto){

        try {            
            ResponseEntity<String> response = restTemplate.getForEntity(AUTHORIZE_URL, String.class);            

            if (response.getStatusCode().is2xxSuccessful()) {
                return  response.getBody().contains("success") &&
                        response.getBody().contains("true");                
            }            
        } catch (Exception e) {
            System.out.println("ExternalAuthorizationService->" +e.getMessage());
            return false;
        }
        
        return false;
    }
}
