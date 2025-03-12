package com.philipe.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.philipe.demo.domains.User;
import com.philipe.demo.dto.TransferDto;
import com.philipe.demo.services.UserService;

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
    public ResponseEntity<String> createUser(@RequestBody User user){

        service.addUser(user);
        return ResponseEntity.ok("");
    } 
}
