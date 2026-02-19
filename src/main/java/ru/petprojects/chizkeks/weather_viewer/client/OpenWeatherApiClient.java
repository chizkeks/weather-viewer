package ru.petprojects.chizkeks.weather_viewer.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.petprojects.chizkeks.weather_viewer.client.response.OpenWeatherResponse;
import ru.petprojects.chizkeks.weather_viewer.properties.OpenWeatherProperties;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class OpenWeatherApiClient {

    @Value("${open-weather.apiKey}")
    private String apiKey;


    private final ObjectMapper objectMapper;

    private final HttpClient httpClient;

    private final OpenWeatherProperties properties;

    public OpenWeatherResponse getWeather(String city) {

        try {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

            String url = "https://api.openweathermap.org/data/2.5/weather?q="
                    + encodedCity
                    + "&appid=" + apiKey
                    + "&units=metric";

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("OpenWeather error: " + response.body());
            }

            return objectMapper.readValue(response.body(), OpenWeatherResponse.class);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error calling OpenWeather API", e);
        }
    }

    public CompletableFuture<OpenWeatherResponse> getWeatherAsync(String city) {

        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);

        String url = String.format(
                "%s/data/2.5/weather?q=%s&appid=%s&units=%s",
                properties.getBaseUrl(),
                encodedCity,
                properties.getApiKey(),
                properties.getUnits()
        );

        return executeWithRetry(buildRequest(url), properties.getMaxRetries());
    }

    public CompletableFuture<OpenWeatherResponse> getWeatherAsync(Double latitude, Double longitude) {

        String url = String.format(
                "%s/data/2.5/weather?lat=%s&lon=%s&appid=%s&units=%s",
                properties.getBaseUrl(),
                latitude,
                longitude,
                properties.getApiKey(),
                properties.getUnits()
        );

        return executeWithRetry(buildRequest(url), properties.getMaxRetries());
    }

    private HttpRequest buildRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(properties.getReadTimeout())
                .GET()
                .build();
    }

    private CompletableFuture<OpenWeatherResponse> executeWithRetry(
            HttpRequest request,
            int retriesLeft
    ) {

        long start = System.currentTimeMillis();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenCompose(response -> {

                    long duration = System.currentTimeMillis() - start;

                    log.info("OpenWeather response status={} duration={}ms",
                            response.statusCode(), duration);

                    if (response.statusCode() == 200) {
                        try {
                            return CompletableFuture.completedFuture(
                                    objectMapper.readValue(
                                            response.body(),
                                            OpenWeatherResponse.class
                                    )
                            );
                        } catch (IOException e) {
                            return CompletableFuture.failedFuture(e);
                        }
                    }

                    if (shouldRetry(response.statusCode()) && retriesLeft > 0) {
                        return retry(request, retriesLeft - 1);
                    }

                    return CompletableFuture.failedFuture(
                            new RuntimeException("OpenWeather error: " + response.body())
                    );
                })
                .exceptionallyCompose(ex -> {

                    log.error("OpenWeather call failed: {}", ex.getMessage());

                    if (retriesLeft > 0) {
                        return retry(request, retriesLeft - 1);
                    }

                    return CompletableFuture.failedFuture(ex);
                });
    }

    private boolean shouldRetry(int status) {
        return status >= 500 || status == 429;
    }

    private CompletableFuture<OpenWeatherResponse> retry(
            HttpRequest request,
            int retriesLeft
    ) {

        long delay = properties.getRetryDelay().toMillis();

        log.warn("Retrying OpenWeather request... retries left={}", retriesLeft);

        return CompletableFuture
                .supplyAsync(() -> null, CompletableFuture.delayedExecutor(delay, TimeUnit.MILLISECONDS))
                .thenCompose(v -> executeWithRetry(request, retriesLeft));
    }
}
