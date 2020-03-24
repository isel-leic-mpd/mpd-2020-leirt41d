import dto.DayInfoDto;
import dto.LocationDto;
import dto.WeatherInfoDto;
import org.junit.Assert;
import org.junit.Test;
import utils.FileRequest;
import utils.HttpRequest;


import java.time.LocalDate;
import java.util.List;

import static java.time.LocalDate.of;
import static queries.lazy.Queries.forEach;

public class WeatherTests {
    private  final double lisbonLat = 38.71667, lisbonLong = -9.13333;

    private static class DayTemps {
        private LocalDate day;
        private List<WeatherInfoDto> temps;


        private DayTemps(LocalDate day, List<WeatherInfoDto> temps) {
            this.day = day; this.temps =temps;
        }
    }
    @Test
    public void pastWheaterAtLisbonFrom2019Jan1Till2019Feb26Test() {

        final int expectedCount = 57;

        WeatherWebApi weather = new WeatherWebApi(new FileRequest());

        Iterable<WeatherInfoDto> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26),
                        24);
        int count[] ={0};
        /*
        for(WeatherInfo wi : past) {
            System.out.println(wi);
            count++;
        }
        */
        forEach(past, item -> {
                    System.out.println(item);
                    count[0]++;
                });

        Assert.assertEquals(expectedCount, count[0]);
    }

    @Test
    public void pastWheaterAtLisbonFrom2019Jan1Till2019Feb26HourlyTest() {

        final int expectedCount = 766;

        WeatherWebApi weather = new WeatherWebApi(new FileRequest());

        Iterable<WeatherInfoDto> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26),
                        1);
        int count[] ={0};
        /*
        for(WeatherInfo wi : past) {
            System.out.println(wi);
            count++;
        }
        */
        forEach(past, item -> {
            System.out.println(item);
            count[0]++;
        });

        Assert.assertEquals(expectedCount, count[0]);
    }





    @Test
    public void pastDaysAtLisbonFrom2019Jan1Till2019Feb26Test() {

        final int expectedCount = 57;

        WeatherWebApi weather = new WeatherWebApi(new FileRequest());

        Iterable<DayInfoDto> past =
                weather.pastDays(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26));
        int count=0;
        for(DayInfoDto di : past) {
            System.out.println(di);
            count++;
        }

        Assert.assertEquals(expectedCount, count);
    }

    @Test
    public void retrieveLocationsNamedLisbonTest() {

        final int expectedCount = 2;

        WeatherWebApi weather = new WeatherWebApi(new HttpRequest());
        Iterable<LocationDto> locations = weather.search("Lisbon");
        int count=0;
        for(LocationDto loc : locations) {
            System.out.println(loc);
            count++;
        }

        Assert.assertEquals(expectedCount, count);
    }





}
