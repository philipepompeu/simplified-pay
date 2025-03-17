package com.philipe.demo.infra.external;


import org.springframework.stereotype.Service;
import com.philipe.demo.application.dto.TransferDto;

@Service
public class ExternalAuthorizationService {
    
    private final ExternalAuthorizationClient externalAuthorizationClient;
    
    public ExternalAuthorizationService(ExternalAuthorizationClient externalAuthorizationClient){
        this.externalAuthorizationClient = externalAuthorizationClient;
    }
    
    public boolean authorize(TransferDto dto){
        try {              
            
            String response = externalAuthorizationClient.authorize();
            return  response.contains("success") && response.contains("true");         
                        
        } catch (Exception e) {
            System.out.println("ExternalAuthorizationService->" +e.getMessage());
            return false;
        }       
        
    }
}
