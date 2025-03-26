package com.nocountry.quo.model.Transaction;

import java.time.LocalDateTime;

import com.nocountry.quo.model.Enums.AssetSymbol;
import com.nocountry.quo.model.Enums.TransactionType;
import com.nocountry.quo.model.ExchangeRates.ExchangeRates;

public record TransactionResponseDto (
    TransactionType type,
    AssetSymbol asset,
    Double quantity,
    Double priceAtTransaction,
    LocalDateTime timestamp,
    ExchangeRates latamRates) {

    public TransactionResponseDto(Transaction t) {
        this(
            t.getType(),
            t.getAsset(),
            t.getQuantity(),
            t.getPriceAtTransaction(),
            t.getTimestamp(),
            t.getLatamRates()
        );
    }
}