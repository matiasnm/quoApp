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
            // Realizar la solicitud a la API
            ResponseEntity<RatesRespondDto> response = restTemplate.getForEntity(finalUrl, RatesRespondDto.class);
            // Comprobar si la respuesta tiene datos
            if (response.getBody() != null) {
                logger.info("Success fetch exchange rates:");
                // Devuelve una respuesta OK con los datos de la API
                return ResponseEntity.ok(response.getBody());
            } else {
                // Si no hay cuerpo en la respuesta, devolvemos error
                logger.error("Error: No data in exchange rates response");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            // En caso de error, logueamos y respondemos con error
            logger.error("Error fetching exchange rate: ", e);
            System.err.println("Error fetching exchange rates: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}