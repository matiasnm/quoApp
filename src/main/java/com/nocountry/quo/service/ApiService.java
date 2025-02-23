package com.nocountry.quo.service;

import lombok.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ApiService {
    // Si la API requiere alguna clave de API o configuración especial.
   // @Value("${api.ejemplo.url}") se comenta esta linea para que no se rompa el proyecto durante el desarrollo
    private String ejemploApiUrl;  //configuramos la URL en el archivo application.properties

    private final RestTemplate restTemplate;

    public ApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Método genérico para hacer una consulta a la API externa
    public String getCryptoData(String cryptoSymbol) {
        // Construye la URL de la API que necesitas consultar
        String url = String.format("%s/api/v3/ticker/price?symbol=%sUSDT", ejemploApiUrl, cryptoSymbol);

        // Realiza la consulta GET y obtiene la respuesta
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        // Retorna el cuerpo de la respuesta (en este caso, el precio de la criptomoneda)
        return response.getBody();
    }
}

