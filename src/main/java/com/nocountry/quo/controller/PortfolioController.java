package com.nocountry.quo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nocountry.quo.model.Enums.AssetSymbol;
import com.nocountry.quo.model.Enums.TransactionType;
import com.nocountry.quo.model.Portfolio.AssetDetailDto;
import com.nocountry.quo.model.Portfolio.PerformanceDto;
import com.nocountry.quo.model.Portfolio.PortfolioOverviewDto;
import com.nocountry.quo.model.Portfolio.TradeRequestDto;
import com.nocountry.quo.model.Transaction.TransactionResponseDto;
import com.nocountry.quo.service.PortfolioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/portfolio")
@SecurityRequirement(name = "bearer-key")
@AllArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;

    @GetMapping("/performance")
    public ResponseEntity<PerformanceDto> getPerformance(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(portfolioService.getPerformance(userDetails));
    }

    @GetMapping("/history")
    public ResponseEntity<List<TransactionResponseDto>> getAllTransactions(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(portfolioService.getAllTransactions(userDetails));
    }

    @GetMapping("/assets/{asset}")
    public ResponseEntity<AssetDetailDto> getAssetDetail(
        @AuthenticationPrincipal UserDetails userDetails,
        @PathVariable AssetSymbol asset) {
        return ResponseEntity.ok(portfolioService.getAssetDetail(asset, userDetails));
    }

    @PostMapping("/trade")
    public ResponseEntity<Void> trade(
        @RequestBody @Valid TradeRequestDto request, 
        @AuthenticationPrincipal UserDetails userDetails) {
        
        if (request.type() == TransactionType.BUY)
            portfolioService.buyAsset(request.asset(), request.quantity(), request.price(), userDetails);
        else
            portfolioService.sellAsset(request.asset(), request.quantity(), request.price(), userDetails);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/overview")
    public ResponseEntity<PortfolioOverviewDto> getOverview(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(portfolioService.getPortfolioOverview(userDetails));
    }
}
