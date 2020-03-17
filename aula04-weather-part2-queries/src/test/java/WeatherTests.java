import dto.DayInfo;
import dto.Location;
import dto.WeatherInfo;
import org.junit.Assert;
import org.junit.Test;
import queries.WeatherQueries;
import utils.FileRequest;
import utils.HttpRequest;

import static queries.WeatherQueries.filter;
import static queries.WeatherQueries.map;
import static queries.WeatherQueries.sum;

import static java.time.LocalDate.of;


public class WeatherTests {
    private  final double lisbonLat = 38.71667, lisbonLong = -9.13333;


    @Test
    public void filterWeatherInfoTest() {

        final int expectedCount = 0;
        WeatherWebApi weather = new WeatherWebApi(new HttpRequest());

        Iterable<WeatherInfo> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2020, 1, 1),
                        of(2020, 1, 26),
                        24);

        for(WeatherInfo wi : past)
            System.out.println(wi);


        Iterable<WeatherInfo> sunnyDays =
                WeatherQueries.filter(
                        past, (WeatherInfo wi) -> wi.getDescription().contains("Sunny"));

        int res=0;
        for(WeatherInfo wi : sunnyDays) res++;

        Assert.assertEquals(expectedCount, res);
    }

    @Test
    public void filterDayInfoTest() {

        final int expectedCount = 14;

        WeatherWebApi weather = new WeatherWebApi(new HttpRequest());

        Iterable<DayInfo> past =
                weather.pastDays(lisbonLat, lisbonLong,
                        of(2020, 1, 1),
                        of(2020, 1, 26));

        for(DayInfo wi : past)
            System.out.println(wi);


        Iterable<DayInfo> brightMoon =
                filter(
                    past, di->  di.getMoonIllum() > 50
                );

        int res=0;
        for(DayInfo wi : brightMoon) res++;

        Assert.assertEquals(expectedCount, res);
    }

    @Test
    public void sumPrecipitationOfDaysWithMaxTempLessThan10Test() {

        final double expectedCount = 1.5;

        WeatherWebApi weather = new WeatherWebApi(new HttpRequest());

        Iterable<WeatherInfo> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2020, 1, 1),
                        of(2020, 1, 26),
                        24);

        /*
        Iterable<WeatherInfo> coldDays =
                WeatherQueries.filter(
                        past, wi -> wi.getTempC() < 15);

        double rainSum = 0.0;

        // an external iteration. This is not what we want

        for(WeatherInfo wi: coldDays) {
            rainSum += wi.getPrecipMM();
        }
        */

        //
        // Our first query based on a call chain of operations
        double rainSum =
                sum(
                    map(
                        filter(past, (WeatherInfo wi) -> wi.getTempC() < 15),
                        wi -> wi.getPrecipMM()
                    )
                );

        // this has exactly the same effect of the above code
        // but is more verbose
        Iterable<WeatherInfo> coldDays =
               filter(past, (WeatherInfo wi) -> wi.getTempC() < 15);
        Iterable<Double> rainValues =
                map(coldDays, wi -> wi.getPrecipMM());
        double rainSum2 =
                sum(rainValues);

        /*
        // This is the form we want for the query. At this time
        // it is no possible, but with some changes it will be!

        double rainSum3 =
                past.filter(wi -> wi.getTempC() < 15).
                map(wi -> wi.getPrecipMM()).
                sum();
        */
        System.out.println("rain total = " + rainSum2 + "mm");


        Assert.assertEquals(expectedCount, rainSum2, 0.01 );
    }



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
