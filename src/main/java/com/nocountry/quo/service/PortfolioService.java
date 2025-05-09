package com.nocountry.quo.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nocountry.quo.model.CoinGeckoApi.CoinsResponseDto;
import com.nocountry.quo.model.Enums.AssetSymbol;
import com.nocountry.quo.model.Enums.TransactionType;
import com.nocountry.quo.model.ExchangeRates.ExchangeRates;
import com.nocountry.quo.model.ExchangerateApi.RatesRespondDto;
import com.nocountry.quo.model.Portfolio.AssetDetailDto;
import com.nocountry.quo.model.Portfolio.PerformanceDto;
import com.nocountry.quo.model.Portfolio.Portfolio;
import com.nocountry.quo.model.Portfolio.PortfolioOverviewDto;
import com.nocountry.quo.model.Transaction.Transaction;
import com.nocountry.quo.model.Transaction.TransactionResponseDto;
import com.nocountry.quo.model.User.User;
import com.nocountry.quo.repository.PortfolioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.*;

@Service
@AllArgsConstructor
public class PortfolioService {

/*
Representa el balance y la tenencia de activos

1️⃣ El usuario inicia con $1000 USD de balance (dinero simulado).
2️⃣ Puede comprar cripto, lo que reduce el balance en USD y aumenta la cantidad de esa cripto.
3️⃣ Puede vender cripto, lo que aumenta el balance en USD y reduce la cantidad de esa cripto.
4️⃣ Puede ver el rendimiento total:
    - Cuánto tendría si convierte todo a USD al precio actual.
    - Cuánto ganó o perdió en comparación con su inversión inicial.
*/

    private PortfolioRepository portfolioRepository;
    private final ExchangerateApiService exchangerateApiService;
    private final CoinGeckoApiService apiService;

    private ExchangeRates getExchangeRatesFromService() {
        RatesRespondDto dto = exchangerateApiService.getLatestExchangeRates();
        return new ExchangeRates(LocalDate.now(), dto.conversion_rates());
    }

    private Portfolio getAuthenticatedPortfolio(UserDetails userDetails) {
        Long userId = ((User) userDetails).getId();
        return portfolioRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("Portfolio not found for user: " + userId));
    }

    public List<TransactionResponseDto> getAllTransactions(UserDetails userDetails) {
        Portfolio portfolio = getAuthenticatedPortfolio(userDetails);
        return portfolio.getTransactions().stream()
            .map(TransactionResponseDto::new)
            .toList();
    }

    public Double getAssetBalance(AssetSymbol assetSymbol, UserDetails userDetails) {
        Portfolio portfolio = getAuthenticatedPortfolio(userDetails);
        return portfolio.getTransactions().stream()
            .filter(t -> t.getAsset().equals(assetSymbol))
            .mapToDouble(t -> t.getType() == TransactionType.BUY ? t.getQuantity() : -t.getQuantity())
            .sum();
    }

    public AssetDetailDto getAssetDetail(AssetSymbol assetSymbol, UserDetails userDetails) {
        Portfolio portfolio = getAuthenticatedPortfolio(userDetails);

        List<Transaction> assetTransactions = portfolio.getTransactions().stream()
            .filter(t -> t.getAsset().equals(assetSymbol))
            .toList();

        double balance = assetTransactions.stream()
            .mapToDouble(t -> t.getType() == TransactionType.BUY ? t.getQuantity() : -t.getQuantity())
            .sum();

        List<TransactionResponseDto> responseDtos = assetTransactions.stream()
            .map(TransactionResponseDto::new)
            .toList();

        return new AssetDetailDto(assetSymbol, balance, responseDtos);
    }

    public PerformanceDto getPerformance(UserDetails userDetails) {
        Portfolio portfolio = getAuthenticatedPortfolio(userDetails);
        double usdBalance = portfolio.getBalance();

        List<CoinsResponseDto> coins = apiService.getCryptoData();
        Map<AssetSymbol, Double> currentPrices = coins.stream()
            .filter(c -> {
                try {
                    AssetSymbol.fromString(c.symbol());
                    return c.currentPrice() != null;
                } catch (IllegalArgumentException e) {
                    return false;
                }
            })
            .collect(Collectors.toMap(
                c -> AssetSymbol.fromString(c.symbol()),
                CoinsResponseDto::currentPrice
            ));

        Map<AssetSymbol, Double> holdings = portfolio.getTransactions().stream()
            .collect(Collectors.groupingBy(Transaction::getAsset,
                Collectors.summingDouble(t -> t.getType() == TransactionType.BUY ? t.getQuantity() : -t.getQuantity())));

        double cryptoValue = holdings.entrySet().stream()
            .mapToDouble(entry -> currentPrices.getOrDefault(entry.getKey(), 0.0) * entry.getValue())
            .sum();

        double totalValue = usdBalance + cryptoValue;

        double invested = portfolio.getTransactions().stream()
            .filter(t -> t.getType() == TransactionType.BUY)
            .mapToDouble(t -> t.getPriceAtTransaction() * t.getQuantity())
            .sum();

        double profitLoss = totalValue - invested;

        return new PerformanceDto(totalValue, profitLoss);
    }

    @Transactional
    public void buyAsset(AssetSymbol asset, double quantity, double price, UserDetails userDetails) {
        Portfolio portfolio = getAuthenticatedPortfolio(userDetails);
        ExchangeRates exchangeRates = getExchangeRatesFromService();

        double totalCost = quantity * price;
        if (portfolio.getBalance() < totalCost) {
            throw new IllegalArgumentException("Insufficient USD balance to buy " + asset);
        }

        portfolio.setBalance(portfolio.getBalance() - totalCost);
        Transaction tx = new Transaction(portfolio, TransactionType.BUY, asset, quantity, price, LocalDateTime.now(), exchangeRates);
        portfolio.getTransactions().add(tx);
        portfolioRepository.save(portfolio);
    }

    @Transactional
    public void sellAsset(AssetSymbol asset, double quantity, double price, UserDetails userDetails) {
        Portfolio portfolio = getAuthenticatedPortfolio(userDetails);

        double assetHolding = getAssetBalance(asset, userDetails);
        if (assetHolding < quantity) {
            throw new IllegalArgumentException("Insufficient " + asset + " to sell. Current holding: " + assetHolding);
        }

        portfolio.setBalance(portfolio.getBalance() + quantity * price);
        ExchangeRates exchangeRates = getExchangeRatesFromService();
        Transaction tx = new Transaction(portfolio, TransactionType.SELL, asset, quantity, price, LocalDateTime.now(), exchangeRates);
        portfolio.getTransactions().add(tx);
        portfolioRepository.save(portfolio);
    }

    public PortfolioOverviewDto getPortfolioOverview(UserDetails userDetails) {
        Portfolio portfolio = getAuthenticatedPortfolio(userDetails);
        List<CoinsResponseDto> coins = apiService.getCryptoData();

        Map<AssetSymbol, Double> currentPrices = coins.stream()
            .filter(c -> {
                try {
                    AssetSymbol.fromString(c.symbol());
                    return c.currentPrice() != null;
                } catch (IllegalArgumentException e) {
                    return false;
                }
            })
            .collect(Collectors.toMap(
                c -> AssetSymbol.fromString(c.symbol()),
                CoinsResponseDto::currentPrice
            ));

        Map<AssetSymbol, Double> holdings = portfolio.getTransactions().stream()
            .collect(Collectors.groupingBy(Transaction::getAsset,
                Collectors.summingDouble(t -> t.getType() == TransactionType.BUY ? t.getQuantity() : -t.getQuantity())));

        Map<AssetSymbol, Double> holdingValues = holdings.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> currentPrices.getOrDefault(e.getKey(), 0.0) * e.getValue()
            ));

        double cryptoValue = holdingValues.values().stream().mapToDouble(Double::doubleValue).sum();
        double totalValue = portfolio.getBalance() + cryptoValue;

        double totalInvested = portfolio.getTransactions().stream()
            .filter(t -> t.getType() == TransactionType.BUY)
            .mapToDouble(t -> t.getPriceAtTransaction() * t.getQuantity())
            .sum();

        double profitLoss = totalValue - totalInvested;
        double profitLossPercentage = (totalValue - 1000.0) / 1000.0 * 100;

        return new PortfolioOverviewDto(
            portfolio.getBalance(),
            holdings,
            holdingValues,
            totalValue,
            totalInvested,
            profitLoss,
            profitLossPercentage
        );
    }
}