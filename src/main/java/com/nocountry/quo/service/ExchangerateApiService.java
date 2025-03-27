package com.nocountry.quo.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nocountry.quo.exception.ExchangeRateApiException;
import com.nocountry.quo.model.ExchangeRates.ExchangeRates;
import com.nocountry.quo.model.ExchangerateApi.RatesRespondDto;
import com.nocountry.quo.repository.ExchangeRateRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExchangerateApiService {
    
    private static final Logger logger = LoggerFactory.getLogger(ExchangerateApiService.class);

    final private String apiKey = "6ff65b8afcdc60c8d1d22737";
    final private String apiUrl = "https://v6.exchangerate-api.com/v6/%s/latest/USD";
    private final RestTemplate restTemplate;
    
    private ExchangeRateRepository exchangeRateRepository;

    public RatesRespondDto getLatestExchangeRates() {
        LocalDate today = LocalDate.now();
    
        return exchangeRateRepository.findByDate(today)
            .map(rates -> {
                logger.info("Retrieved exchange rates from the database.");
                return new RatesRespondDto(rates.getDate().toEpochDay(), rates.getRates());
            })
            .orElseGet(() -> {
                logger.info("No rates found in the database. Fetching from external API...");
                RatesRespondDto apiRates = getExchangeRate();
                exchangeRateRepository.save(new ExchangeRates(today, apiRates.conversion_rates()));
                logger.info("Rates fetched from the API and saved to the database.");
                return apiRates;
            });
    }

    public RatesRespondDto getExchangeRate() {
        String finalUrl = String.format(apiUrl, apiKey);
        try {
            ResponseEntity<RatesRespondDto> response = restTemplate.getForEntity(finalUrl, RatesRespondDto.class);
        
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("Exchange rate API request completed successfully.");
                return response.getBody();
            } else {
                throw new ExchangeRateApiException("No valid data was received from the API.");
            }
        
        } catch (Exception e) {
            logger.error("Error while calling the exchange rate API: ", e);
            throw new ExchangeRateApiException("Failed to fetch exchange rates from the API: " + e.getMessage());
        }  
    }

}