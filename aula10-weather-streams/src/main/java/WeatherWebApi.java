import dto.DayInfoDto;
import dto.LocationDto;
import dto.WeatherInfoDto;
import utils.Request;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.stream.Stream;

import static queries.lazy.Queries.*;

public class WeatherWebApi {
    private static final String API_KEY;
    private static final String WEATHER_SERVICE =  "http://api.worldweatheronline.com/premium/v1/";
    private static final String WEATHER_PAST_TEMPLATE =
            "past-weather.ashx?q=%s&date=%s&enddate=%s&tp=%d&format=csv&key=%s";
    private static final String WEATHER_SEARCH_TEMPLATE = "search.ashx?query=%s&format=tab&key=%s";
    private final Request req;

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

    public WeatherWebApi(Request req) {

        this.req = req;
    }

    public Stream<WeatherInfoDto> pastWeather(double latitude, double longitude, LocalDate from, LocalDate to, int period) {
        String query = latitude + "," + longitude;
        String path =  WEATHER_SERVICE + String.format(WEATHER_PAST_TEMPLATE, query, from, to, period,API_KEY);

        /*
         O código a seguir ao comentário é equivalente a este dentro do comentário:

        Iterable<String> iter0 = dropWhile(req.getContent(path), s -> s.startsWith("#"));
        Iterable<String> iter1 = skip(iter0, 1);
        Iterable<String> iter2 = filter(iter1, s-> s.contains("worldweatheronline"));
        Iterable<WeatherInfoDto> iter3 = map(iter2, s -> WeatherInfoDto.valueOf(s));
       */

        return req.getContent(path)
                .dropWhile( s -> s.startsWith("#"))
                .skip(1)
                .filter(s-> s.contains("worldweatheronline"))
                .map(WeatherInfoDto::valueOf);
        /*
        return map(
                filter(
                    skip(
                        dropWhile(req.getContent(path), s -> s.startsWith("#")),
                    1
                    ),
                    s -> s.contains("worldweatheronline")
                ),
                s -> WeatherInfoDto.valueOf(s)
            );
         */



    }


    public Stream<DayInfoDto> pastDays(double latitude, double longitude, LocalDate from, LocalDate to) {
        String query = latitude + "," + longitude;
        String path =  WEATHER_SERVICE + String.format(WEATHER_PAST_TEMPLATE, query, from, to, 24, API_KEY);

        /*
        return map(
                filter(
                        skip(
                                dropWhile(req.getContent(path), s -> s.startsWith("#")),
                                1
                        ),
                        s -> {
                            boolean res = !s.contains("worldweatheronline");
                            return res;
                        }
                ),
                s -> DayInfoDto.valueOf(s)
        );
        */

        return req.getContent(path)
                .dropWhile( s -> s.startsWith("#"))
                .skip(1)
                .filter(s-> !s.contains("worldweatheronline"))
                .map(DayInfoDto::valueOf);
    }

    public Stream<LocationDto> search(String location) {

        String path =  WEATHER_SERVICE + String.format(WEATHER_SEARCH_TEMPLATE, location, API_KEY);

        /*
        Iterable<String> it = req.getContent(path);
        return map(
                dropWhile(it, s -> s.startsWith("#")),
                s -> LocationDto.valueOf(s)
        );
        */
        return req.getContent(path)
                .dropWhile(s -> s.startsWith("#"))
                .map(LocationDto::valueOf);
    }



}
