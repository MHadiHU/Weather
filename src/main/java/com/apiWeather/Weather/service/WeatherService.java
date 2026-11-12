package com.apiWeather.Weather.service;

import com.apiWeather.Weather.client.WeatherClient;
import com.apiWeather.Weather.dto.WeatherResponse;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Service
public class WeatherService {

    // Let Spring inject it. Don't use 'new'!
    private final WeatherClient weatherClient;
    private  ObjectMapper objectMapper;

    public WeatherService(WeatherClient weatherClient, ObjectMapper objectMapper) {
        this.weatherClient = weatherClient;
        this.objectMapper = objectMapper;
    }

    @Cacheable(value = "weather", key = "#city")
    public WeatherResponse getWeather(String city){
            String rawJson = weatherClient.getWeatherJson(city);

                JsonNode rootNode = objectMapper.readTree(rawJson);
                String realCityName = rootNode.path("name").asText();
                Double realTemperature = rootNode.path("main").path("temp").asDouble();
                String realDescription = rootNode.path("weather").get(0).path("description").asText();

                return new WeatherResponse(realCityName,realTemperature,realDescription );


    }
}