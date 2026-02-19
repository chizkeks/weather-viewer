package ru.petprojects.chizkeks.weather_viewer.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.petprojects.chizkeks.weather_viewer.client.response.OpenWeatherResponse;
import ru.petprojects.chizkeks.weather_viewer.model.Weather;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OpenWeatherApiMapper {
    @Mapping(source = "name", target = "city")
    @Mapping(source = "sys.country", target = "country")

    @Mapping(source = "main.temp", target = "temperature")
    @Mapping(source = "main.feelsLike", target = "feelsLike")

    @Mapping(source = "main.pressure", target = "pressure")
    @Mapping(source = "main.humidity", target = "humidity")

    @Mapping(source = "weather", target = "icon", qualifiedByName = "mapIcon")

    @Mapping(source = "wind.speed", target = "windSpeed")
    @Mapping(source = "coord.lat", target = "latitude")
    @Mapping(source = "coord.lon", target = "longitude")
    Weather toWeather(OpenWeatherResponse response);

    @Named("mapIcon")
    default String mapIcon(List<OpenWeatherResponse.Weather> weather) {
        if (weather == null || weather.isEmpty()) {
            return null;
        }
        return weather.get(0).getIcon();
    }
}
