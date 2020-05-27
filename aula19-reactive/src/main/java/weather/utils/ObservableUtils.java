package weather.utils;

import io.reactivex.Observable;

import java.util.concurrent.CompletableFuture;

public class ObservableUtils {
    public static <T> Observable<T> fromCompletableFuture(
            CompletableFuture<T> fut) {
        return Observable.create( subscriber -> {
            fut.whenComplete((T val, Throwable fault) -> {
                if (fault == null) {
                    subscriber.onNext(val);
                    subscriber.onComplete();
                } else {
                    subscriber.onError(fault);
                }
            });
        });
    }
}
