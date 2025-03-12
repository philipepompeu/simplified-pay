package com.philipe.demo.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.philipe.demo.dto.TransferDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/transfer")
@Tag(name="Simplified Pay", description="Simplified payment system")
public class TransferController {    
    
    @PostMapping()
    @Operation(summary = "Transfer money from payer to payee")
    @ApiResponse(responseCode = "200", description = "Money was transfered")
    @ApiResponse(responseCode = "400", description = "Error occurred")
    public ResponseEntity<String> transferMoney(@RequestBody TransferDto transfer){

        return ResponseEntity.ok("");
    }
}
