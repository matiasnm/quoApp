package com.nocountry.quo.model.Portfolio;

import java.util.Map;

import com.nocountry.quo.model.Enums.AssetSymbol;

public record PortfolioOverviewDto(
    double usdBalance,
    Map<AssetSymbol, Double> cryptoHoldings,
    Map<AssetSymbol, Double> cryptoValuesInUsd,
    double totalValue,
    double totalInvestedUsd,
    double profitLossUsd,
    double profitLossPercentage
) {}