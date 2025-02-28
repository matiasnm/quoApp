package com.nocountry.quo.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nocountry.quo.model.ExchangeRates.ExchangeRates;
import com.nocountry.quo.model.ExchangerateApi.RatesRespondDto;
import com.nocountry.quo.repository.ExchangeRateRepository;

import java.util.Optional;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Service
public class ExchangerateApiService {
    
    private static final Logger logger = LoggerFactory.getLogger(ExchangerateApiService.class);

    final private String apiKey = "6ff65b8afcdc60c8d1d22737";
    final private String apiUrl = "https://v6.exchangerate-api.com/v6/%s/latest/USD";
    private final RestTemplate restTemplate;
    
    private ExchangeRateRepository exchangeRateRepository;

    public ResponseEntity<RatesRespondDto> getLatestExchangeRates() {
        LocalDate today = LocalDate.now();

         // Intentar recuperar las tasas desde la base de datos
        Optional<ExchangeRates> exchangeRatesOpt = exchangeRateRepository.findByDate(today);
        if (exchangeRatesOpt.isPresent()) {
            logger.info("Recuperando tasas de cambio desde la base de datos.");
            ExchangeRates exchangeRates = exchangeRatesOpt.get();

            // Convertir la entidad a DTO y devolver
            RatesRespondDto ratesDto = new RatesRespondDto(
                exchangeRates.getDate().toEpochDay(),  // O ajustar según cómo se guarda la fecha
                exchangeRates.getRates()
            );
            return ResponseEntity.ok(ratesDto);
        }

        logger.info("No se encontraron tasas en la base de datos. Consultando la API...");
        ResponseEntity<RatesRespondDto> response = getExchangeRate();

        if (response.getBody() != null) {
            // Guardar los datos en la base de datos antes de devolverlos
            ExchangeRates exchangeRates = new ExchangeRates(today, response.getBody().conversion_rates());
            exchangeRateRepository.save(exchangeRates);
            logger.info("Tasas obtenidas de la API y guardadas en la base de datos.");
        }
    
        return response;
    }

    
    public ResponseEntity<RatesRespondDto> getExchangeRate() {

        String finalUrl = String.format(apiUrl, apiKey);
        try {
            // Realizar la solicitud a la API
            ResponseEntity<RatesRespondDto> response = restTemplate.getForEntity(finalUrl, RatesRespondDto.class);
            // Comprobar si la respuesta tiene datos
            if (response.getBody() != null) {
                logger.info("Consulta a la API de tasas de cambio realizada exitosamente.");
                // Devuelve una respuesta OK con los datos de la API
                return ResponseEntity.ok(response.getBody());
            } else {
                // Si no hay cuerpo en la respuesta, devolvemos error
                logger.error("Error: Consulta a la API de tasas de cambio SIN datos.");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            // En caso de error, logueamos y respondemos con error
            logger.error("Error en consulta a la API de tasas de cambio: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}