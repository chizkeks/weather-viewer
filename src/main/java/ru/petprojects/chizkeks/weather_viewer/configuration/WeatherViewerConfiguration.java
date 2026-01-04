package ru.petprojects.chizkeks.weather_viewer.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import ru.petprojects.chizkeks.weather_viewer.properties.Properties;

@Configuration
@EnableConfigurationProperties(Properties.class)
public class WeatherViewerConfiguration {
}
