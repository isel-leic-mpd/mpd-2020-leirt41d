import dto.DayInfo;
import dto.Location;
import dto.WeatherInfo;
import org.junit.Assert;
import org.junit.Test;
import utils.FileRequest;
import utils.HttpRequest;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.time.LocalDate.of;


public class WeatherTests {
    private  final double lisbonLat = 38.71667, lisbonLong = -9.13333;

    /*
    @Test
    public void filterTest() {

        final int expectedCount = 1;

        WeatherWebApi weather = new WeatherWebApi(new HttpRequest());

        Iterable<WeatherInfo> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2020, 1, 1),
                        of(2020, 2, 26),
                        24);

        for(WeatherInfo wi : past)
            System.out.println(wi);

        Iterable<WeatherInfo> sunnyDays = WeatherQueries.filter(past, wi -> wi.getDescription().contains("Sunny"));

        int res=0;
        for(WeatherInfo wi : sunnyDays) res++;

        Assert.assertEquals(expectedCount, res);
    }
    */
    
    @Test
    public void pastWheaterAtLisbonFrom2019Jan1Till2019Feb26Test() {

        final int expectedCount = 57;

        WeatherWebApi weather = new WeatherWebApi(new FileRequest());

        Iterable<WeatherInfo> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26),
                        24);
        int count= 0;

        for(WeatherInfo wi : past) {
            System.out.println(wi);
            count++;
        }

        Assert.assertEquals(expectedCount, count);
    }




    @Test
    public void pastDaysAtLisbonFrom2019Jan1Till2019Feb26Test() {

        final int expectedCount = 57;

        WeatherWebApi weather = new WeatherWebApi(new FileRequest());

        Iterable<DayInfo> past =
                weather.pastDays(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 26));
        int count=0;
        for(DayInfo di : past) {
            System.out.println(di);
            count++;
        }

        Assert.assertEquals(expectedCount, count);
    }

    @Test
    public void retrieveLocationsNamedLisbonTest() {

        final int expectedCount = 2;

        WeatherWebApi weather = new WeatherWebApi( new FileRequest());
        Iterable<Location> locations = weather.search("Lisbon");
        int count=0;
        for(Location loc : locations) {
            System.out.println(loc);
            count++;
        }

        Assert.assertEquals(expectedCount, count);
    }

}
