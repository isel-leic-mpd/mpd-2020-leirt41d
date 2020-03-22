import org.junit.Assert;
import org.junit.Test;

import static java.time.LocalDate.of;

public class WeatherTests {
    private  final double lisbonLat = 38.71667, lisbonLong = -9.13333;

    @Test
    public void pastWheaterAtLisbonFrom2020Jan1Till2020Feb1Test() {
        final int expectedCount = 32;

        WeatherWebApi weather = new WeatherWebApi();

        Iterable<WeatherInfo> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2020, 1, 1),
                        of(2020, 2, 1));
        int count=0;
        for(WeatherInfo wi : past) {
            System.out.println(wi);
            count++;
        }

        Assert.assertEquals(expectedCount, count);
    }

    /*
    @Test
    public void pastDaysAtLisbonFrom2020Jan1Till2020Jan26Test() {
        final int expectedCount = 26;

        WeatherWebApi weather = new WeatherWebApi();

        Iterable<DayInfo> past =
                weather.pastDays(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 1, 26));
        int count=0;
        for(DayInfo di : past) {
            System.out.println(di);
            count++;
        }

        Assert.assertEquals(expectedCount, count);
    }
    */

    @Test
    public void retrieveLocationsNamedLisbonTestPart1() {
        final int expectedCount = 2;

        WeatherWebApi weather = new WeatherWebApi();
        Iterable<Location> locations = weather.search("Lisbon");
        int count=0;
        for(Location loc : locations) {
            System.out.println(loc);
            count++;
        }

        Assert.assertEquals(expectedCount, count);
    }
}
