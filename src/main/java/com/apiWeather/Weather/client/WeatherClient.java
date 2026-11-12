package com.apiWeather.Weather.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class WeatherClient {

    // 1. Add the curly braces!
    @Value("${weather.api.url}")
    private String apiUrl;

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestClient restClient;

    // 2. Use a constructor to build the RestClient AFTER the values are injected
    public WeatherClient(
            @Value("${weather.api.url}")
            String apiUrl,

            @Value("${weather.api.key}")
            String apiKey
    ) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.restClient = RestClient.builder()
                .baseUrl(apiUrl)
                .build();
    }

    public String getWeatherJson(String city){
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/data/2.5/weather")
                        .queryParam("q", city)
                        .queryParam("appid", apiKey)
                        .queryParam("units", "metric")
                        .build()
                ).retrieve()
                .body(String.class);
    }
}