package com.nocountry.quo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nocountry.quo.model.CoinGeckoApi.CoinsResponseDto;
import com.nocountry.quo.service.CoinGeckoApiService;
import java.util.*;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/coins")
@AllArgsConstructor
public class CoinsController {

    private final CoinGeckoApiService apiService;

    @GetMapping("")
    public ResponseEntity<List<CoinsResponseDto>> get() {
        List<CoinsResponseDto> coins = apiService.getCryptoData();
        return ResponseEntity.ok(coins);
    }
    
}