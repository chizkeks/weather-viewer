package ru.petprojects.chizkeks.weather_viewer.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class Properties {

    public record OpenWeatherApi (
        @NotBlank String key
    ) {}
}
