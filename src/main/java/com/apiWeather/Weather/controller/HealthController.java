package com.apiWeather.Weather.controller;

import com.apiWeather.Weather.dto.WeatherResponse;
import com.apiWeather.Weather.service.WeatherService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "https://weather-frontend-five.vercel.app")
public class HealthController {

    // Let Spring inject it. Don't use 'new'!
    private final WeatherService weatherService;

    public HealthController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/ping")
    public String showMessage(){
        return "pong";
    }

    @GetMapping("/api/weather")
    public WeatherResponse getWeather(@RequestParam String city){
        return weatherService.getWeather(city);
    }
}