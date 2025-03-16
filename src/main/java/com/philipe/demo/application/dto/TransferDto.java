package com.philipe.demo.application.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
    /**
     * Value to be transfered to <payee>.
     */
    private BigDecimal value;  
    /**
     * The id of the user that will get the money withdraw.
    */
    @Schema(description = "The payer numeric id")
    private long payer;
    /**
     * The id of the user that will receive get the money.
    */
    @Schema(description = "The payeee numeric id")
    private long payee;    
    /**
     * Date of the transaction was created.
    */
    private LocalDateTime createdAt;
}
