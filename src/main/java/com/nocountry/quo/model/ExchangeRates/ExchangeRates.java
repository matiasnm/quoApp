package com.nocountry.quo.model.ExchangeRates;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.*;
import lombok.*;

import com.nocountry.quo.model.Enums.LocalCurrency;

@Entity
@Table(name = "exchange_rates")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRates {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Se guarda una tasa de cambio por día!
    @Column(nullable = false, unique = true)
    private LocalDate date;

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "rate")
    @CollectionTable(name = "exchange_rate_details", joinColumns = @JoinColumn(name = "exchange_rate_id"))
    private Map<LocalCurrency, Double> rates = new HashMap<>();

    public ExchangeRates(LocalDate date, Map<LocalCurrency, Double> rates) {
        this.date = date;
        this.rates = rates;
    }
}