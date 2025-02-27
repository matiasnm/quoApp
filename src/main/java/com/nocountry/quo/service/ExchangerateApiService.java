package com.nocountry.quo.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nocountry.quo.model.ExchangerateApi.RatesRespondDto;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExchangerateApiService {
    
    final private String apiKey = "6ff65b8afcdc60c8d1d22737";
    final private String apiUrl = "https://v6.exchangerate-api.com/v6/%s/latest/USD";

    private static final Logger logger = LoggerFactory.getLogger(ExchangerateApiService.class);

    private final RestTemplate restTemplate;

    public ResponseEntity<RatesRespondDto> getExchangeRate() {

        String finalUrl = String.format(apiUrl, apiKey);

        try {
            ResponseEntity<RatesRespondDto> response = restTemplate.getForEntity(finalUrl, RatesRespondDto.class);
            logger.info("Success fetch exchange rate:");
            System.err.println("Success fetch! exchange rates");
            return response;
        } catch (Exception e) {
            // Log the error to see details
            logger.error("Error fetching exchange rate: ", e);
            System.err.println("Error fetching exchange rates: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  
        }
    }

}