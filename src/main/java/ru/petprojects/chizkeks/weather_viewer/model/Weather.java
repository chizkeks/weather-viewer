package ru.petprojects.chizkeks.weather_viewer.model;

import lombok.Builder;

@Builder
public record Weather(
        String city,
        String country,
        Double temperature,
        Double feelsLike,
        Integer pressure,
        Integer humidity,
        Double windSpeed,
        String icon,
        Double latitude,
        Double longitude
) {
}

