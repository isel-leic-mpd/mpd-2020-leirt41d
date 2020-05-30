package observables_examples;

import io.reactivex.Observable;

import java.util.Random;

public class Observables {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {

        }
    }
    public static void mergeExample() {


        Observable<Long> numbers1 =
                Observable.create(source ->
                    new Thread(() -> {
                        Random r = new Random();
                        for(long i=0; i < 20; ++i) {
                            source.onNext(i);
                            long next = r.nextLong();
                            sleep( Math.abs(next)  % 3);
                        }
                    }).start()
                );


        Observable<Long> numbers2 =
                Observable.create(source -> {
                    new Thread(() -> {
                        Random r = new Random();
                        for(long i=20; i < 40; ++i) {
                            source.onNext(i);
                            long next = r.nextLong();
                            sleep(Math.abs(next) % 3);
                        }
                    }).start();
                });

        Observable.merge(numbers1, numbers2)
        .subscribe(System.out::println);
    }
}
