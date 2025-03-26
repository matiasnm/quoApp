package com.nocountry.quo.model.Portfolio;

import java.util.List;
import com.nocountry.quo.model.Enums.AssetSymbol;
import com.nocountry.quo.model.Transaction.TransactionResponseDto;

public record AssetDetailDto(
    AssetSymbol asset,
    double balance,
    List<TransactionResponseDto> transactions
) {}
