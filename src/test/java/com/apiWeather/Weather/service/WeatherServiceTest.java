package com.apiWeather.Weather.service;

import com.apiWeather.Weather.client.WeatherClient;
import com.apiWeather.Weather.dto.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tools.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WeatherServiceTest {

    @Mock
    private WeatherClient weatherClient;
    private ObjectMapper objectMapper;
    private WeatherService weatherService;

    @BeforeEach
    void setUp(){
        objectMapper = new ObjectMapper();
        weatherService = new WeatherService(weatherClient, objectMapper);
    }
    @Test
    void testGetWeather_HappyPath(){
        String city = "Kabul";
        String fakeJson = "{\"name\":\"Kabul\",\"main\":{\"temp\":25.5},\"weather\":[{\"description\":\"clear sky\"}]}";

        when(weatherClient.getWeatherJson(city)).thenReturn(fakeJson);

        WeatherResponse response = weatherService.getWeather(city);

        assertNotNull(response);
        assertEquals("Kabul", response.getCity());
        assertEquals(25.5, response.getTemperature());
        assertEquals("clear sky", response.getDescription());

        verify(weatherClient, times(1)).getWeatherJson(city);
    }

    @Test
    void testGetWeather_InvalidCityThrowsException(){
        String city = "FakeCity";

        when(weatherClient.getWeatherJson(city))
                .thenThrow(new org.springframework.web.client.HttpClientErrorException(
                        org.springframework.http.HttpStatus.NOT_FOUND
                ));

        assertThrows(org.springframework.web.client.HttpClientErrorException.class, () -> {
            weatherClient.getWeatherJson(city);
        });
    }
}