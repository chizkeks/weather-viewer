package ru.petprojects.chizkeks.weather_viewer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.petprojects.chizkeks.weather_viewer.client.OpenWeatherApiClient;
import ru.petprojects.chizkeks.weather_viewer.model.Weather;
import ru.petprojects.chizkeks.weather_viewer.model.mapper.OpenWeatherApiMapper;

import java.util.concurrent.CompletableFuture;


@Service
@RequiredArgsConstructor
public class OpenWeatherApiService {

    private final OpenWeatherApiClient client;
    private final OpenWeatherApiMapper mapper;

    public CompletableFuture<Weather> getWeather(String city) {
        return client.getWeatherAsync(city)
                .thenApply(mapper::toWeather);
    }

    public CompletableFuture<Weather> getWeather(Double latitude, Double longitude) {
        return client.getWeatherAsync(latitude, longitude)
                .thenApply(mapper::toWeather);
    }
}


