
import io.reactivex.*;

import io.reactivex.disposables.Disposable;
import weather.WeatherReactiveService;
import weather.WeatherAsyncWebApi;
import weather.model.*;

import org.junit.Test;
import weather.utils.CounterRequest;
import weather.utils.HttpRequest;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static java.time.LocalDate.of;
import static java.util.function.Function.identity;
import static org.junit.Assert.assertEquals;


public class WeatherReactiveServiceTests {

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch(InterruptedException e) {

        }
    }

    private static Observable<Location>
    getLisbon(WeatherReactiveService service) {
        return service.search("Lisboa")
                .filter(l -> l.getCountry().equalsIgnoreCase("Portugal"))
                .filter(l -> l.getName().equalsIgnoreCase("Lisboa"))
                .firstElement()
                .toObservable();
    }

    @Test
    public void retrieveLocationsNamedLisbonTest() {

        final long expectedCount = 1;

        long nLocs=0;

        assertEquals(expectedCount,  nLocs);
    }

    @Test
    public void maxTemperatureDayAtLisbonInMarch2019() {
        LocalDate expectedDate = of(2019,3,15);

        LocalDate first = of(2019,3,1);
        LocalDate last = of(2019,3,31);


        WeatherReactiveService weather = new WeatherReactiveService(
                new WeatherAsyncWebApi(new HttpRequest()));

        Maybe<DayInfo> maxDay = getLisbon(weather)
        .flatMap(l -> l.getDays(first, last))
        .reduce((d1, d2) -> d1.getMaxTemp() > d2.getMaxTemp() ? d1 : d2);


        DayInfo dMax = maxDay.blockingGet();
        DayInfo dMax2 = maxDay.blockingGet();

        assertEquals(expectedDate, dMax.getDate() );
    }

    private static void threadInfo(String msg) {
        System.out.printf("%s: in thread %s\n",
                msg, Thread.currentThread().getName());
    }

    @Test
    public void getMoonPhaseOfDateAtLisbon2019_02_12Test() {
        String expectedPhase = "First Quarter";
        WeatherReactiveService weather = new WeatherReactiveService(
                new WeatherAsyncWebApi(new HttpRequest()));
        LocalDate feb12 = of(2019, 2, 12);

        String[] moonPhase= {null};

        System.out.println("Create Observable!");

        Observable<DayInfo> day =
                getLisbon(weather)
                .doOnNext( l -> {
                    threadInfo("new Location");
                    System.out.println(l);
                })
                .flatMap(l -> l.getDays(feb12, feb12))
                .doOnNext(d -> {
                    threadInfo("new DayInfo");
                    System.out.println(d);
                });

        CompletableFuture<Void> done = new CompletableFuture<>();

        System.out.println("Subscribe Observable!");
        day.subscribe(new Observer<DayInfo>() {

            @Override
            public void onSubscribe(Disposable d) {
                threadInfo("onSubscribe");
            }

            @Override
            public void onError(Throwable e) {
                threadInfo("onError");
                System.out.println(e.getMessage());
            }

            @Override
            public void onComplete() {
                threadInfo("onComplete");
                done.complete(null);
            }

            @Override
            public void onNext(DayInfo t) {
                threadInfo("onNext");
                System.out.println(t);
                moonPhase[0] = t.getMoonPhase();
            }
        });

        done.join();

        assertEquals(expectedPhase, moonPhase[0] );
        System.out.println("end of test");

    }


    @Test
    public void getTemperaturesOf2and3FebTest() {
        WeatherReactiveService weather =
                new WeatherReactiveService(new WeatherAsyncWebApi(new HttpRequest()));
        LocalDate start_date = of(2019, 2, 2);
        LocalDate end_date = of(2019, 2, 3);

        Observable<WeatherInfo> temps =
            getLisbon(weather)
            .flatMap(l -> l.getDays(start_date, end_date))
            .flatMap( d -> d.getTemperatures());

        long[] countTemps = {0};
        CompletableFuture<Void> done = new CompletableFuture<>();

        temps.subscribe(new Observer() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(Object o) { countTemps[0]++;  }

            @Override
            public void onError(Throwable e) {
                done.completeExceptionally(e);
            }

            @Override
            public void onComplete() {
                done.complete(null);
            }
        });


        temps.subscribe(
                wi -> System.out.println(wi));
        done.join();


        /*
        Single<Long> countTemp = temps.count();

        long countTemps = countTemp.blockingGet();
        */

        // to complete
        assertEquals(48, countTemps[0] );
    }


    @Test
    public void multipleObserversTest() {
        Observable<Integer> numbers =
                Observable.just(2,3, 4);

        numbers.subscribe(System.out::println);

        numbers.subscribe(System.out::println);

    }

    @Test
    public void maxTemperatureDayAtLisbonIn2019() {
        // in this test we should found the hottest day in 2019 at Lisbon
        // for this test we must send at least twelve requests
        // to the service since the date period is limited rougly to a month
        // to that purpose we define a list with the month days from jan. to dec.
        Integer[]  monthDays= {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        CounterRequest req = new CounterRequest(new HttpRequest());
        WeatherReactiveService weather =
                new WeatherReactiveService(new WeatherAsyncWebApi(req));

        long start = System.currentTimeMillis();


        Observable<DayInfo> maxTempDay =
                getLisbon(weather)
                .flatMap(l -> {
                    Observable<Integer> o1 =
                           Observable.range(1, 12);

                    Observable<Integer> o2 = Observable.fromArray(monthDays);

                    Observable<Observable<DayInfo>> oo = o1.zipWith(o2, (i1, i2 ) ->
                           l.getDays(LocalDate.of(2019, i1, 1),
                                     LocalDate.of(2019, i1, i2)));
                    return oo;


                    // to complete
                })
                .flatMap( o -> o);

        maxTempDay.subscribe(System.out::println);

        sleep(10000);

        int expectedMaxTemp = 36;
        DayInfo maxDay = null;
        LocalDate expectedDate = LocalDate.of(2019,5,30);

        //System.out.println("Now get the day");

        // validations
        System.out.println("done in " + (System.currentTimeMillis()-start) + "ms");

        System.out.println("Requests number: " + req.getCount());
        assertEquals(expectedMaxTemp, maxDay.getMaxTemp());
        assertEquals(expectedDate, maxDay.getDate());

    }

}
