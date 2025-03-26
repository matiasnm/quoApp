package com.nocountry.quo.model.Transaction;

import java.time.LocalDateTime;

import com.nocountry.quo.model.Enums.AssetSymbol;
import com.nocountry.quo.model.Enums.TransactionType;
import com.nocountry.quo.model.ExchangeRates.ExchangeRates;
import com.nocountry.quo.model.Portfolio.Portfolio;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "\"transactions\"")
@Entity(name = "Transaction")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private AssetSymbol asset;

    @Column(nullable = false)
    private Double quantity;

    @Column(nullable = false)
    private Double priceAtTransaction;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "exchange_rate_id")
    private ExchangeRates latamRates;

    public Transaction(Portfolio portfolio, TransactionType type, AssetSymbol asset, double quantity,
            double priceAtTransaction, LocalDateTime timestamp, ExchangeRates latamRates) {
        this.portfolio = portfolio;
        this.type = type;
        this.asset = asset;
        this.quantity = quantity;
        this.priceAtTransaction = priceAtTransaction;
        this.timestamp = timestamp;
        this.latamRates = latamRates;
    }

}