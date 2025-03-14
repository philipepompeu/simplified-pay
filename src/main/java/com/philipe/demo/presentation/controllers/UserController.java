package com.philipe.demo.presentation.controllers;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.philipe.demo.application.dto.UserDto;
import com.philipe.demo.application.services.UserService;
import com.philipe.demo.domains.model.UserEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name="Users")
public class UserController {
    
    @Autowired
    private UserService service;

    @PostMapping()
    @Operation(summary = "Create new user")
    @ApiResponse(responseCode = "200", description = "New user added")
    @ApiResponse(responseCode = "400", description = "Fail to create user")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user){

        try {
            UserDto newUser = service.addUser(user);
            
            return ResponseEntity.ok(newUser);    
        } catch (Exception e) {            
            System.out.println(String.format("Error on POST/users [ %s ]", e.getMessage()));

            return ResponseEntity.unprocessableEntity().build();
        }        
        
    } 
}
