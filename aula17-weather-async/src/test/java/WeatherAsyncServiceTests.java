import model.DayInfo;
import model.Location;
import model.WeatherInfo;
import org.junit.Test;
import utils.FileRequest;
import utils.HttpRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;
import static queries.naive.EagerQueries.count;
import static queries.lazy.Queries.*;


public class WeatherAsyncServiceTests {

    @Test
    public void retrieveLocationsNamedLisbonTest() {

        final long expectedCount = 2;

        WeatherAsyncService weather = new WeatherAsyncService(
                new WeatherAsyncWebApi(new HttpRequest()));

        long nLocs = weather
                .search("Lisboa")
                .thenApply(s -> s.collect(toList()))
                .whenComplete((l, e) -> l.forEach(System.out::println))
                .thenApply(l -> l.size())
                .join();

        assertEquals(expectedCount,  nLocs);
    }

    @Test
    public void maxThermicAmplitudeAtLisbonInMarch2019() {
        LocalDate expectedDate = LocalDate.of(2019,3,15);

        LocalDate first = LocalDate.of(2019,3,1);
        LocalDate last = LocalDate.of(2019,3,31);

        WeatherAsyncService weather =
                new WeatherAsyncService(new WeatherAsyncWebApi(new HttpRequest()));

        LocalDate maxAmplitudeDate = null;
        // to complete
        // try to build just one async pipeline and do only join to that
        // pipeline


        assertEquals(expectedDate, maxAmplitudeDate);

    }



    @Test
    public void getMoonPhaseOfDateAtLisbon2019_02_12Test() {
        String expectedPhase = "First Quarter";
        WeatherAsyncService weather = new WeatherAsyncService(
                new WeatherAsyncWebApi(new HttpRequest()));

        String moonPhase = null;
        // to complete
        // try to build just one async pipeline and do only join to that
        // pipeline

        assertEquals(expectedPhase, moonPhase);
    }


    @Test
    public void getTemperaturesOf2and3FebTest() {
        WeatherAsyncService weather =
                new WeatherAsyncService(new WeatherAsyncWebApi(new HttpRequest()));

        CompletableFuture<Stream<WeatherInfo>> futTemps =  null;
        // to complete
        // try to build just one async pipeline and do only join to that
        // pipeline

        assertEquals(48, futTemps.join().count());

    }



}
