package com.philipe.demo.application.dto;

import java.math.BigDecimal;

import com.philipe.demo.domains.enums.ClientType;
import com.philipe.demo.domains.enums.UserType;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    
    private Long id;

    @Schema(description = "User name", example = "User FullName")
    @NotBlank
    private String fullName;
    
    @Schema(description = "User email", example="email@provider.com")
    @NotBlank
    @NotNull
    private String email;    
    
    @Schema(example="49457178009")
    @NotNull
    private String legalIdentifier;
    
    private UserType userType;
    
    private ClientType clientType;

    private String password;

    @Schema(description = "The user current balance", example = "1000.00")
    @Min(1)
    private BigDecimal balance;
}
