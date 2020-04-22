import dto.DayInfoDto;
import dto.LocationDto;
import dto.WeatherInfoDto;
import org.junit.Test;
import utils.CounterRequest;
import utils.FileRequest;
import utils.HttpRequest;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static java.time.LocalDate.of;
import static org.junit.Assert.assertEquals;
import static queries.lazy.Queries.count;
import static queries.lazy.Queries.forEach;

public class WeatherWebApiTests {
    private  final double lisbonLat = 38.71667, lisbonLong = -9.13333;

    @Test
    public void pastWheaterAtLisbonFrom2019Jan1Till2019Feb26Test() {

        final int expectedCount = 57;

        CounterRequest req;

        WeatherWebApi weather = new WeatherWebApi(
                req = new CounterRequest(new  FileRequest()));

        Stream<WeatherInfoDto> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26),
                        24);

        assertEquals(0, req.getCount());
        long countWeather = past.count();
        assertEquals(1, req.getCount());
        assertEquals(expectedCount, countWeather );
    }

    /*
    @Test
    public void pastWheaterAtLisbonFrom2019Jan1Till2019Feb26HourlyTest() {

        final int expectedCount = 766;

        WeatherWebApi weather = new WeatherWebApi(new FileRequest());

        Iterable<WeatherInfoDto> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26),
                        1);
        long countWeather = count(past);

        assertEquals(expectedCount, countWeather );

    }


    @Test
    public void pastDaysAtLisbonFrom2019Jan1Till2019Feb26Test() {

        final int expectedCount = 57;

        WeatherWebApi weather = new WeatherWebApi(new FileRequest());

        Iterable<DayInfoDto> pastDays =
                weather.pastDays(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26));

        long countDays = count(pastDays);

        assertEquals(expectedCount, countDays );
    }

    @Test
    public void retrieveLocationsNamedLisbonTest() {

        final int expectedCount = 2;

        WeatherWebApi weather = new WeatherWebApi(new HttpRequest());
        Iterable<LocationDto> locations = weather.search("Lisbon");

        long countLocations = count(locations);

        assertEquals(expectedCount, countLocations );
    }
    */
}
