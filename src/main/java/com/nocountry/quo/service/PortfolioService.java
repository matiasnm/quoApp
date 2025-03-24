package com.nocountry.quo.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nocountry.quo.model.Enums.AssetSymbol;
import com.nocountry.quo.model.Enums.LocalCurrency;
import com.nocountry.quo.model.Enums.TransactionType;
import com.nocountry.quo.model.Portfolio.Portfolio;
import com.nocountry.quo.model.Transaction.Transaction;
import com.nocountry.quo.repository.PortfolioRepository;
import com.nocountry.quo.repository.TransactionRepository;

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
    private TransactionRepository transactionRepository;

/*


    public double getCryptoBalance(AssetSymbol assetSymbol) {
        return transactions.stream()
            .filter(t -> t.getAsset().equals(assetSymbol))
            .mapToDouble(t -> t.getType() == TransactionType.BUY ? t.getQuantity() : -t.getQuantity())
            .sum();
    }

    public double getTotalValue(Map<AssetSymbol, Double> marketPrices) {
        double cryptoValue = transactions.stream()
            .filter(t -> marketPrices.containsKey(t.getAsset()))
            .mapToDouble(t -> getCryptoBalance(t.getAsset()) * marketPrices.get(t.getAsset()))
            .sum();
        return balance + cryptoValue;
    }    

    public Portfolio buyCrypto(Long portfolioId, AssetSymbol assetSymbol, double quantity, double pricePerUnit) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        double totalCost = quantity * pricePerUnit;
        if (portfolio.getBalance() < totalCost) {
            throw new RuntimeException("Not enough USD balance");
        }

        portfolio.setBalance(portfolio.getBalance() - totalCost);
        Transaction transaction = new Transaction(portfolio, TransactionType.BUY, assetSymbol, quantity, pricePerUnit, LocalDateTime.now());
        portfolio.getTransactions().add(transaction);

        transactionRepository.save(transaction);
        return portfolioRepository.save(portfolio);
    }

    public Portfolio sellCrypto(Long portfolioId, AssetSymbol assetSymbol, double quantity, double pricePerUnit) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new RuntimeException("Portfolio not found"));

        double currentHolding = portfolio.getCryptoBalance(assetSymbol);
        if (currentHolding < quantity) {
            throw new RuntimeException("Not enough crypto balance");
        }

        double totalRevenue = quantity * pricePerUnit;
        portfolio.setBalance(portfolio.getBalance() + totalRevenue);
        Transaction transaction = new Transaction(portfolio, TransactionType.SELL, assetSymbol, quantity, pricePerUnit, LocalDateTime.now()); //latamRatesAtTransaction
        portfolio.getTransactions().add(transaction);

        transactionRepository.save(transaction);
        return portfolioRepository.save(portfolio);
    }

    public double getPortfolioValue(Long portfolioId, Map<AssetSymbol, Double> marketPrices) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
            .orElseThrow(() -> new RuntimeException("Portfolio not found"));
        return portfolio.getTotalValue(marketPrices);
    } */
}