import flow.Subscriber;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import weather.WeatherReactiveService;
import weather.WeatherAsyncWebApi;
import weather.model.*;

import org.junit.Test;
import weather.utils.HttpRequest;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;


public class WeatherReactiveServiceTests {

    private static Single<Location>
    getLisbon(WeatherReactiveService service) {
        return service.search("Lisboa")
                .filter(l -> l.getCountry().equalsIgnoreCase("Portugal"))
                .filter(l -> l.getName().equalsIgnoreCase("Lisboa"))
                .firstElement()
                .toSingle();
    }

    @Test
    public void retrieveLocationsNamedLisbonTest() {

        final long expectedCount = 1;

        long nLocs=0;

        assertEquals(expectedCount,  nLocs);
    }



    @Test
    public void maxTemperatureDayAtLisbonInMarch2019() {
        LocalDate expectedDate = LocalDate.of(2019,3,15);

        LocalDate first = LocalDate.of(2019,3,1);
        LocalDate last = LocalDate.of(2019,3,31);

        LocalDate maxTempDayInMarch2019 = null;
        WeatherReactiveService weather = new WeatherReactiveService(
                new WeatherAsyncWebApi(new HttpRequest()));

        Maybe<DayInfo> maxDay = getLisbon(weather)
        .toObservable()
        .flatMap(l -> l.getDays(first, last))
        .reduce((d1, d2) -> d1.getMaxTemp() > d2.getMaxTemp() ? d1 : d2);

        DayInfo dMax = maxDay.blockingGet();
        assertEquals(expectedDate, dMax.getDate() );
    }




    @Test
    public void getMoonPhaseOfDateAtLisbon2019_02_12Test() {
        String expectedPhase = "First Quarter";
        WeatherReactiveService weather = new WeatherReactiveService(
                new WeatherAsyncWebApi(new HttpRequest()));
        LocalDate feb12 = LocalDate.of(2019, 2, 12);

        String moonPhase=null;
         // to complete
        // try to build just one async pipeline and do only join to that
        // pipeline

        assertEquals(expectedPhase, moonPhase );
    }


    @Test
    public void getTemperaturesOf2and3FebTest() {
        WeatherReactiveService weather =
                new WeatherReactiveService(new WeatherAsyncWebApi(new HttpRequest()));
        LocalDate start_date = LocalDate.of(2019, 2, 2);
        LocalDate end_date = LocalDate.of(2019, 2, 3);


        long countTemps= 0;
        // to complete
        assertEquals(48, countTemps );
    }


    @Test
    public void maxTemperatureDayAtLisbonIn2019() {
        // in this test we should found the hottest day in 2019 at Lisbon
        // for this test we must send at least twelve requests
        // to the service since the date period is limited rougly to a month
        // to that purpose we define a list with the month days from jan. to dec.
        Integer[]  monthDays= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        WeatherReactiveService weather =
                new WeatherReactiveService(new WeatherAsyncWebApi(new HttpRequest()));

        // to complete

        // validations
        //assertEquals(expectedMaxTemp, maxTempDay.getMaxTemp());
        //assertEquals(expectedDate, maxTempDay.getDate());

    }

}
