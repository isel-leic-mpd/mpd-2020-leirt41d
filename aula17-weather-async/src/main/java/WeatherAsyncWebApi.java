import dto.DayInfoDto;
import dto.LocationDto;
import dto.WeatherInfoDto;
import utils.AsyncRequest;
import utils.Request;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static queries.lazy.Queries.*;

public class WeatherAsyncWebApi {
    private static final String API_KEY;
    private static final String WEATHER_SERVICE =  "http://api.worldweatheronline.com/premium/v1/";
    private static final String WEATHER_PAST_TEMPLATE =
            "past-weather.ashx?q=%s&date=%s&enddate=%s&tp=%d&format=csv&key=%s";
    private static final String WEATHER_SEARCH_TEMPLATE = "search.ashx?query=%s&format=tab&key=%s";
    private final AsyncRequest req;

    private static String getApiKeyFromResources() {
        try {
            URL keyFile = ClassLoader.getSystemResource("worldweatheronline-app-key.txt");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
                return reader.readLine();
            }
        }
        catch(IOException e) {
            throw new IllegalStateException(
                    "YOU MUST GET a KEY from developer.worldweatheronline.com and place it in src/main/resources/worldweatheronline-app-key.txt");
        }
    }

    static {
        API_KEY = getApiKeyFromResources();
    }

    public WeatherAsyncWebApi(AsyncRequest req) {
        this.req = req;
    }



    public CompletableFuture<Stream<DayInfoDto>>
    pastDays(double latitude, double longitude, LocalDate from, LocalDate to) {
        String query = latitude + "," + longitude;
        String path =  WEATHER_SERVICE +
            String.format(WEATHER_PAST_TEMPLATE, query, from, to, 24, API_KEY);


        return req.getContent(path)
                .thenApply(stream ->
                    stream
                    .dropWhile( s -> s.startsWith("#"))
                    .skip(1)
                    .filter(s-> !s.contains("worldweatheronline"))
                    .map(DayInfoDto::valueOf)
                );
    }

    public CompletableFuture<Stream<LocationDto>>
    search(String location) {

        String path =  WEATHER_SERVICE +
            String.format(WEATHER_SEARCH_TEMPLATE, location, API_KEY);


        return req.getContent(path)
               .thenApply(stream ->
                    stream
                    .dropWhile(s -> s.startsWith("#"))
                    .map(LocationDto::valueOf)
               );
    }

}
