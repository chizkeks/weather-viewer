package ru.petprojects.chizkeks.weather_viewer.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Data
@Configuration
@ConfigurationProperties(prefix = "open-weather")
public class OpenWeatherProperties {

    private String baseUrl;
    private String apiKey;
    private String units = "metric";

    private Duration connectTimeout = Duration.ofSeconds(5);
    private Duration readTimeout = Duration.ofSeconds(5);

    private int maxRetries = 3;
    private Duration retryDelay = Duration.ofMillis(500);
}

