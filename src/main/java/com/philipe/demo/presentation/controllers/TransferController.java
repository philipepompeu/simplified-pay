package com.philipe.demo.presentation.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.philipe.demo.application.dto.TransferDto;
import com.philipe.demo.application.services.TransferService;
import com.philipe.demo.presentation.exception.RequestValidationException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/transfer")
@Tag(name="Simplified Pay", description="Simplified payment system")
public class TransferController {    

    @Autowired
    private TransferService transferService;

    public TransferController(TransferService transferService){
        this.transferService = transferService;
    }
    
    @PostMapping()
    @Operation(summary = "Transfer money from payer to payee")
    @ApiResponse(responseCode = "200", description = "Money was transfered")
    @ApiResponse(responseCode = "422", description = "Validation failed")
    @ApiResponse(responseCode = "400", description = "Bad Request")
    public ResponseEntity<?> transferMoney(@RequestBody TransferDto transfer){

        try {
            return ResponseEntity.ok(this.transferService.transferMoney(transfer));
        } catch (RequestValidationException e) {
            return ResponseEntity.unprocessableEntity().body(e.getMessage());
        } catch (Exception e) {            
            System.out.println(String.format("Error [ %s ]", e.getMessage() ));
            return ResponseEntity.badRequest().build();
        }
        
    }
}
