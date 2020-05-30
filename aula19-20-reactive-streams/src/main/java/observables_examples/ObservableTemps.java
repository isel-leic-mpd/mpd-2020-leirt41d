package observables_examples;

import flowtemps.TempInfo;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class ObservableTemps {

    public static void main(String[] args) {
        Observable<TempInfo> temps =
        Observable.fromPublisher(TempPublisher.getTemperatures("New York"))
        .map(t -> new TempInfo( t.getTown(),
                (t.getTemp() - 32) * 5 / 9));

        temps.subscribe(new Observer<TempInfo>() {
            Disposable subscription;

            @Override
            public void onSubscribe( Disposable d ) {
                this.subscription = d;
            }

            @Override
            public void onNext( TempInfo tempInfo ) {
                System.out.println( tempInfo );
            }

            @Override
            public void onError( Throwable t ) {
                System.err.println(t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Done!");
            }
        });


    }
}
