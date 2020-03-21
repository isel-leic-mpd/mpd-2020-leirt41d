import dto.DayInfo;
import dto.Location;
import dto.WeatherInfo;
import org.junit.Assert;
import org.junit.Test;
import utils.FileRequester;
import utils.HttpRequester;

// import static queries.WeatherQueries.filter;
// import static queries.WeatherQueries.map;
import static queries.WeatherQueries.sum;

import static queries.generic.Queries.filter;
import static queries.generic.Queries.map;
//import static queries.generic.Queries.reduce;

import static java.time.LocalDate.of;


public class WeatherTests {
    private  final double lisbonLat = 38.71667, lisbonLong = -9.13333;


    @Test
    public void filterWeatherInfoFebSunnyDaysCountTest() {

        final int expectedCount = 0;
        WeatherWebApi weather = new WeatherWebApi(new HttpRequester());

        Iterable<WeatherInfo> past =
                weather.pastWeather(lisbonLat, lisbonLong,
                        of(2020, 1, 1),
                        of(2020, 1, 26),
                        24);

        for(WeatherInfo wi : past)
            System.out.println(wi);


        Iterable<WeatherInfo> sunnyDays =
                filter(
                        past, (WeatherInfo wi) -> wi.getDescription().contains("Sunny"));

        int res=0;
        for(WeatherInfo wi : sunnyDays) res++;

        Assert.assertEquals(expectedCount, res);
    }

    @Test
    public void filterDayInfoBrightMoonTest() {

        final int expectedCount = 14;

        WeatherWebApi weather = new WeatherWebApi(new HttpRequester());

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
    public void sumPrecipitationOfDaysWithMaxTempLessThan15Test() {

        final double expectedCount = 1.5;

        WeatherWebApi weather = new WeatherWebApi(new HttpRequester());

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
                        filter(past, wi -> wi.getTempC() < 15),
                        wi -> wi.getPrecipMM()
                    )
                );


        // the next code has exactly the same effect of the above code
        // but is more verbose

        // intermediate operations
        Iterable<WeatherInfo> coldDays =
               filter(past, (WeatherInfo wi) -> wi.getTempC() < 15);
        Iterable<Double> rainValues =
                map(coldDays, wi -> wi.getPrecipMM());
        // terminal operation
        double rainSum2 = sum(rainValues);

        /*
        // The next is the form that we want for the query. At this time
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
    public void sumPrecipitationOfDaysWithMaxTempLessThan15WithReduceTest() {
        // A COMPLETAR
    }


    @Test
    public void pastWheaterAtLisbonFrom2019Jan1Till2019Feb26Test() {

        final int expectedCount = 57;

        WeatherWebApi weather = new WeatherWebApi(new FileRequester());

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

        WeatherWebApi weather = new WeatherWebApi(new FileRequester());

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

    /*
    @Test
    public void retrieveLocationsNamedLisbonTest() {

        final int expectedCount = 2;

        WeatherWebApi weather = new WeatherWebApi( new FileRequester());
        Iterable<Location> locations = weather.search("Lisbon");
        int count=0;
        for(Location loc : locations) {
            System.out.println(loc);
            count++;
        }

        Assert.assertEquals(expectedCount, count);
    }

     */

}
