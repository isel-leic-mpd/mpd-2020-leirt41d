import model.DayInfo;
import model.Location;
import org.junit.Test;
import utils.HttpRequest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static queries.MIterable.*;


public class WeatherServiceTests1 {
    /*
    @Test
    public void retrieveLocationsNamedLisbonTest() {

        final int expectedCount = 2;

        WeatherService weather = new WeatherService(new WeatherWebApi(new HttpRequest()));
        Iterable<Location> locations = weather.search("Lisbon");
        int count=0;
        for(Location loc : locations) {
            System.out.println(loc);
            count++;
        }

        assertEquals(expectedCount, count);
    }

    @Test
    public void getMoonPhaseOfDateAtLisbon2019_02_12() {
        String expectedPhase = "First Quarter";

        // To implement
    }
    */

    @Test
    public void getDays2and3Feb() {
        WeatherService weather =
                new WeatherService(new WeatherWebApi(new HttpRequest()));
        /*
        Optional<Location> lisbon =
            first(
                filter(
                    weather.search("Lisbon"),
                    l -> l.getCountry()
                            .equalsIgnoreCase("portugal")
                )
            );
        */
        Optional<Location> lisbon =
                from(weather.search("Lisbon"))
                .filter(l -> l.getCountry().equalsIgnoreCase("portugal"))
                .first();


        assertTrue(lisbon.isPresent());

        LocalDate day1 = LocalDate.of(2019, 2, 2);
        LocalDate day2 = LocalDate.of(2019, 2, 3);

        Iterable<DayInfo> days = lisbon.get().getDays(day1,day2);

        /*
        long count =
            count(
                flatMap( days,
                    d -> d.getTemperatures()
                )
            );
        */

        long count  =
                from(days)
                .flatMap(d -> from(d.getTemperatures()))
                .count();

        assertEquals(48, count);
    }

    /*
    @Test
    public void maxThermicAmplitudeAtLisbonInMarch2019() {
        LocalDate expectedDate = LocalDate.of(2019,3,15);
        WeatherService weather =
                new WeatherService(new WeatherWebApi(new HttpRequest()));

        Optional<Location> lisbonOpt =
            first(
                filter(
                    weather.search("Lisbon"),
                    l -> l.getCountry()
                            .equalsIgnoreCase("portugal")
                )
            );

        assertTrue(lisbonOpt.isPresent());
        Location lisbon = lisbonOpt.get();
        LocalDate first = LocalDate.of(2019,3,1);
        LocalDate last = LocalDate.of(2019,3,20);

        Optional<DayInfo> maxThermalAmplitude=Optional.empty();

        // To Complete!

        assertTrue(maxThermalAmplitude.isPresent());
        DayInfo max = maxThermalAmplitude.get();

        assertEquals(expectedDate, max.getDate());

    }
    */
}
