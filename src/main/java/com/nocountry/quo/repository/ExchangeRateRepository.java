package com.nocountry.quo.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nocountry.quo.model.ExchangeRates.ExchangeRates;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRates, Long> {
    
    Optional<ExchangeRates> findByDate(LocalDate date);
    boolean existsByDate(LocalDate date);
}
