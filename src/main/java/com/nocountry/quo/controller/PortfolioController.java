package com.nocountry.quo.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nocountry.quo.model.Enums.AssetSymbol;
import com.nocountry.quo.model.Portfolio.AssetDetailDto;
import com.nocountry.quo.model.Portfolio.PerformanceDto;
import com.nocountry.quo.model.Portfolio.PortfolioOverviewDto;
import com.nocountry.quo.model.Transaction.TransactionResponseDto;
import com.nocountry.quo.service.PortfolioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/portfolio")
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/performance")
    public PerformanceDto getPerformance(@AuthenticationPrincipal UserDetails userDetails) {
        return portfolioService.getPerformance(userDetails);
    }

    @GetMapping("/transactions")
    public List<TransactionResponseDto> getAllTransactions(@AuthenticationPrincipal UserDetails userDetails) {
        return portfolioService.getAllTransactions(userDetails);
    }
    
    @GetMapping("/{asset}")
    public AssetDetailDto getAssetDetail(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable AssetSymbol asset) {
        return portfolioService.getAssetDetail(asset, userDetails);
    }

    @PostMapping("/buy")
    public void buyAsset(
        @RequestParam AssetSymbol asset,
        @RequestParam double quantity,
        @RequestParam double price,
        @AuthenticationPrincipal UserDetails userDetails) {
        portfolioService.buyAsset(asset, quantity, price, userDetails);
    }

    @PostMapping("/sell")
    public void sellAsset(
        @RequestParam AssetSymbol asset,
        @RequestParam double quantity,
        @RequestParam double price,
        @AuthenticationPrincipal UserDetails userDetails) {
        portfolioService.sellAsset(asset, quantity, price, userDetails);
    }

    @GetMapping("/overview")
    public PortfolioOverviewDto getOverview(@AuthenticationPrincipal UserDetails userDetails) {
        return portfolioService.getPortfolioOverview(userDetails);
    }
}
