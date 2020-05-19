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

        final int expectedCount = 2;

        WeatherAsyncService weather = new WeatherAsyncService(
                new WeatherAsyncWebApi(new HttpRequest()));
        // to complete
    }


    @Test
    public void getMoonPhaseOfDateAtLisbon2019_02_12Test() {
        String expectedPhase = "First Quarter";
        WeatherAsyncService weather = new WeatherAsyncService(
                new WeatherAsyncWebApi(new HttpRequest()));

        String moonPhase = null;
        // to complete

        assertEquals(expectedPhase, moonPhase);
    }


    @Test
    public void getTemperaturesOf2and3FebTest() {
        WeatherAsyncService weather =
                new WeatherAsyncService(new WeatherAsyncWebApi(new HttpRequest()));

        CompletableFuture<Stream<WeatherInfo>> futTemps =  null;
        // to complete


        assertEquals(48, futTemps.join().count());

    }


    @Test
    public void maxThermicAmplitudeAtLisbonInMarch2019() {
        LocalDate expectedDate = LocalDate.of(2019,3,15);

        LocalDate first = LocalDate.of(2019,3,1);
        LocalDate last = LocalDate.of(2019,3,31);

        WeatherAsyncService weather =
                new WeatherAsyncService(new WeatherAsyncWebApi(new HttpRequest()));

        LocalDate maxAmplitude =
                weather.search("Lisbon")
                .thenApply(stream ->
                        stream.filter( l -> l.getCountry()
                                .equalsIgnoreCase("portugal"))
                                .findFirst()
                                .get())
                .thenCompose(loc -> loc.getDays(first, last))
                .thenApply(stream ->
                    stream.max( (d1, d2) ->
                         (d1.getMaxTemp() - d1.getMinTemp()) - (d2.getMaxTemp() - d2.getMinTemp()))
                    .get()
               )
                .join().getDate();



    }

}
