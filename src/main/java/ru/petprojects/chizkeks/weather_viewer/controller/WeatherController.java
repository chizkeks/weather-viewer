package ru.petprojects.chizkeks.weather_viewer.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.petprojects.chizkeks.weather_viewer.model.Weather;
import ru.petprojects.chizkeks.weather_viewer.service.OpenWeatherApiService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/weather")
@RequiredArgsConstructor
public class WeatherController {

    private final OpenWeatherApiService service;

    @GetMapping("/city")
    public CompletableFuture<Weather> getWeatherByCity(
            @RequestParam("city") String city
    ) {
        return service.getWeather(city);
    }

    @GetMapping("/location")
    public CompletableFuture<Weather> getWeatherByLocation(
            @RequestParam("lat") Double latitude,
            @RequestParam("lon") Double longitude
    ) {
        return service.getWeather(latitude, longitude);
    }
}
