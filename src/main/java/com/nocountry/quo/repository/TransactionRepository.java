package com.nocountry.quo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nocountry.quo.model.Transaction.Transaction;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByPortfolioId(Long id);
    
}
