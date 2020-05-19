import dto.DayInfoDto;
import dto.LocationDto;
import dto.WeatherInfoDto;
import org.junit.Test;
import utils.CounterRequest;
import utils.HttpRequest;


import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.time.LocalDate.of;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;

public class WeatherAsyncWebApiTests {
    private  final double lisbonLat = 38.71667, lisbonLong = -9.13333;

    @Test
    public void pastWheaterAtLisbonFrom2019Jan1Till2019Feb26Test() {

        final int expectedCount = 35;

        CounterRequest req;

        WeatherAsyncWebApi weather = new WeatherAsyncWebApi(
                req = new CounterRequest(new  HttpRequest()));

        CompletableFuture<Stream<WeatherInfoDto>> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26),
                        24);

        long countWeather = past.join().count();
        assertEquals(1, req.getCount());
        assertEquals(expectedCount, countWeather );
    }


    @Test
    public void pastWheaterAtLisbonFrom2019Jan1Till2019Feb26HourlyTest() {

        final int expectedCount = 838;

        WeatherAsyncWebApi weather = new WeatherAsyncWebApi(new HttpRequest());

        CompletableFuture<Stream<WeatherInfoDto>> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26),
                        1);

        long countWeather = past.join().count();

        assertEquals(expectedCount, countWeather );

    }


    @Test
    public void pastDaysAtLisbonFrom2019Jan1Till2019Feb26Test() {

        final int expectedCount = 35;

        WeatherAsyncWebApi weather = new WeatherAsyncWebApi(new HttpRequest());

        CompletableFuture<Stream<DayInfoDto>> pastDays =
                weather.pastDays(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26));

        long countDays = pastDays.join().count();

        assertEquals(expectedCount, countDays );
    }


    @Test
    public void retrieveLocationsNamedLisbonTest() {

        final int expectedCount = 2;

        WeatherAsyncWebApi weather = new WeatherAsyncWebApi(new HttpRequest());
        CompletableFuture<Stream<LocationDto>>
            futLocations = weather.search("Lisbon");

        List<LocationDto> locations =
                futLocations.join().collect(toList());
        long countLocations = locations.size();
        locations.forEach(System.out::println);
        assertEquals(expectedCount, countLocations );
    }

}
