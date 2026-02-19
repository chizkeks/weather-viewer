package ru.petprojects.chizkeks.weather_viewer.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.petprojects.chizkeks.weather_viewer.properties.OpenWeatherProperties;
import ru.petprojects.chizkeks.weather_viewer.properties.Properties;

import java.net.http.HttpClient;

@Configuration
@EnableConfigurationProperties(Properties.class)
public class WeatherViewerConfiguration {

    @Bean
    public HttpClient httpClient(OpenWeatherProperties properties) {
        return HttpClient.newBuilder()
                .connectTimeout(properties.getConnectTimeout())
                .version(HttpClient.Version.HTTP_2)
                .build();
    }
}
