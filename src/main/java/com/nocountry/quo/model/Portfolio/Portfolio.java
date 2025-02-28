package com.nocountry.quo.model.Portfolio;

import jakarta.persistence.*;
import lombok.*;

import com.nocountry.quo.model.Transaction.Transaction;
import com.nocountry.quo.model.User.User;

import java.util.ArrayList;
import java.util.List;

@Table(name = "\"portfolios\"")
@Entity(name = "Portfolio")
@Getter
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    
    private Double balance;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    public Portfolio(User user) {
        this.user = user;
        this.balance = 1000.0;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
