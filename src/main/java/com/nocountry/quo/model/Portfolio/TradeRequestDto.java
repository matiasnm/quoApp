package com.nocountry.quo.model.Portfolio;

import com.nocountry.quo.model.Enums.AssetSymbol;
import com.nocountry.quo.model.Enums.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

public record TradeRequestDto(
    @NotNull
    AssetSymbol asset,

    @NotNull
    @DecimalMin(value = "0.0001", inclusive = true)
    Double quantity,

    @NotNull
    @DecimalMin(value = "0.01", inclusive = true)
    Double price,
    
    @NotNull
    TransactionType type) {}
