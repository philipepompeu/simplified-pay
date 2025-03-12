package com.philipe.demo.dto;

import java.math.BigDecimal;

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
    
    private BigDecimal value;

    @Schema(description = "The payer numeric id")
    private long payer;
    @Schema(description = "The payeee numeric id")
    private long payee;
}
