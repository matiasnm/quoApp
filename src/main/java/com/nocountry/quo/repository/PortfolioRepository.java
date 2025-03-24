package com.nocountry.quo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nocountry.quo.model.Portfolio.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    
}