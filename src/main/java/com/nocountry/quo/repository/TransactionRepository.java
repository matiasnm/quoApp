package com.nocountry.quo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nocountry.quo.model.Transaction.Transaction;

public interface TransactionRepository  extends JpaRepository<Transaction, Long> {
    
}
