package ru.petprojects.chizkeks.weather_viewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class WeatherViewerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherViewerApplication.class, args);
	}

}
