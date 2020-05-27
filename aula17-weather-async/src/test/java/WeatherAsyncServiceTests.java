import model.DayInfo;
import model.Location;
import model.WeatherInfo;
import org.junit.Test;

import utils.HttpRequest;
import utils.Pair;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.concurrent.CompletableFuture.allOf;
import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.rangeClosed;
import static org.junit.Assert.*;
import static utils.Pair.pair;
import static utils.StreamUtils.zip;


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

    private static CompletableFuture<Location> getLisbon(WeatherAsyncService service) {
        return service.search("Lisboa")
                .thenApply(
                        slocs -> slocs.filter(l -> l.getCountry().equalsIgnoreCase("Portugal"))
                                .filter(l -> l.getName().equalsIgnoreCase("Lisboa"))
                                .findFirst()
                                .get()
                );
    }

    @Test
    public void maxTemperatureDayAtLisbonInMarch2019() {
        LocalDate expectedDate = LocalDate.of(2019,3,15);

        LocalDate first = LocalDate.of(2019,3,1);
        LocalDate last = LocalDate.of(2019,3,31);

        WeatherAsyncService weather =
                new WeatherAsyncService(new WeatherAsyncWebApi(new HttpRequest()));

        LocalDate maxTempDate = null;

        CompletableFuture<DayInfo> maxTempDayInMarch2019  = null;



        // to complete
        // try to build just one async pipeline and do only join to that
        // pipeline


        assertEquals(expectedDate, maxTempDate);

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
        LocalDate start_date = LocalDate.of(2019, 2, 2);
        LocalDate end_date = LocalDate.of(2019, 2, 3);

        CompletableFuture<Long> futTemps =  null;
        // to complete
        // try to build just one async pipeline and do only join to that
        // pipeline
        /*
        futTemps =
                getLisbon(weather)
                .thenCompose(l ->
                    weather.pastWeather(
                            l, start_date, end_date, 1)
                )
                .thenApply( s -> s.count());
        */

        /*
        futTemps = getLisbon(weather)
        .thenCompose(l -> l.getDays(start_date, end_date))
        .thenCompose( stream -> {
            CompletableFuture<Long>[] tempCounts =
                    stream.map( d -> d.getTemperatures()
                             .thenApply(s -> s.count()))
                    .toArray(sz -> new CompletableFuture[sz]);

            return allOf(tempCounts)
            .thenApply(__ ->
                Arrays.stream(tempCounts)
                .mapToLong(cf -> cf.join())
                .sum()
            );
        });
         */

        futTemps = getLisbon(weather)
                .thenCompose(l -> l.getDays(start_date, end_date))
                .thenCompose( stream ->
                    stream.map( d -> d.getTemperatures())
                    .reduce((cf1, cf2) ->
                        cf1.thenCombine(cf2, (l1, l2) ->
                                Stream.concat(l1,l2))).get()
                )
                .thenApply(s -> s.count());


        assertEquals(48, futTemps.join().intValue());

    }

    @Test
    public void maxTemperatureDayAtLisbonIn2019() {
        // in this test we should found the hottest day in 2019 at Lisbon
        // for this test we must send at least twelve requests
        // to the service since the date period is limited rougly to a month
        // to that purpose we define a list with the month days from jan. to dec.
        Integer[]  monthDays= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        WeatherAsyncService weather =
                new WeatherAsyncService(new WeatherAsyncWebApi(new HttpRequest()));


        CompletableFuture<Location> lisbon =
                weather.search("Lisboa")
                        .thenApply(stream ->
                                stream
                                .filter(l -> l.getCountry().equalsIgnoreCase("Portugal"))
                                .filter(l -> l.getName().equalsIgnoreCase("Lisboa"))
                                .findFirst()
                                .get()
                        );


        // paralelize requests
        CompletableFuture<CompletableFuture<Stream<DayInfo>>[]>
                daysPerMonth2019 =
                lisbon.thenApply(l -> {
                    // to Complete
                    return null;
                });

        // get the intermediary CompletableFuture results
        CompletableFuture<Stream<DayInfo>> all2019DayInfo = null;
        // to Complete from daysPerMonth2019.


        // obtaing the 2019 max temp day (blocking oiperation)
        DayInfo day = null;
        // to complete from all2019DayInfo.


        int expectedMaxTemp = 36;
        LocalDate expectedDate = LocalDate.of(2019,5,30);

        // validations
        assertEquals(expectedMaxTemp, day.getMaxTemp());
        assertEquals(expectedDate, day.getDate());

    }

}
