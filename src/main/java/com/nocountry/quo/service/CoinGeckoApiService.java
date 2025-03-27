package com.nocountry.quo.service;

import lombok.*;

import java.io.IOException;
import java.util.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.nocountry.quo.exception.ExternalApiException;
import com.nocountry.quo.model.CoinGeckoApi.CoinsResponseDto;

import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CoinGeckoApiService {

    private final String coingeckoUrl = "https://api.coingecko.com/api/v3/coins/markets";
    private final RestTemplate restTemplate;

    public ResponseEntity<List<CoinsResponseDto>> getCryptoData() {
        List<String> customCryptos = List.of("binancecoin", "polkadot", "bitcoin", "ethereum", "litecoin", "solana", "cardano", "tether", "ripple", "dai");
        String ids = String.join(",", customCryptos);
        String url = coingeckoUrl + "?vs_currency=usd&ids=" + ids + "&order=market_cap_desc&per_page=" + customCryptos.size() + "&page=1&sparkline=false";

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            List<CoinsResponseDto> coinsList = mapper.readValue(response.getBody(), new TypeReference<>() {});
            return ResponseEntity.ok(coinsList);

        } catch (RestClientException e) {
            throw new ExternalApiException("Error calling CoinGecko: " + e.getMessage());
        } catch (IOException e) {
            throw new ExternalApiException("Error parsing CoinGecko response: " + e.getMessage());
        }
    }
}