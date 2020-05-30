package weather.utils;

import io.reactivex.Observable;

import java.util.concurrent.CompletableFuture;

public class ObservableUtils {

    /**
     * Non blocking version for convert a CompletableFuture into an
     * observable
     * @param cf
     * @param <T>
     * @return
     */
    public static <T> Observable<T>
    fromCompletableFuture( CompletableFuture<T> cf) {
        return Observable.create(subscriber -> {
            cf
            .whenComplete((t, e) -> {
                if (e == null) {
                    subscriber.onNext(t);
                    subscriber.onComplete();
                }
                else {
                    subscriber.onError(e);
                }
            });
        });
    }
}
