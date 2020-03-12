import org.junit.Assert;
import org.junit.Test;

import static java.time.LocalDate.of;

public class WeatherTests {
    private  final double lisbonLat = 38.71667, lisbonLong = -9.13333;

    @Test
    public void pastWheaterAtLisbonFrom2019Jan1Till2019Feb1Test() {
        final int expectedCount = 32;

        WeatherWebApi weather = new WeatherWebApi();

        Iterable<WeatherInfo> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2019, 1, 1),
                        of(2019, 2, 1));
        int count=0;
        for(WeatherInfo wi : past) {
            System.out.println(wi);
            count++;
        }

        Assert.assertEquals(expectedCount, count);
    }

    @Test
    public void pastDaysAtLisbonFrom2019Jan1Till2019Feb26Test() {
        final int expectedCount = 32;

        WeatherWebApi weather = new WeatherWebApi();

        Iterable<DayInfo> past =
                weather.pastDays(lisbonLat, lisbonLong, of(2019, 1, 1), of(2019, 2, 26));
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
