package observables_examples;

import flowtemps.TempInfo;

import org.reactivestreams.Publisher;



public class TempPublisher {
    public static Publisher<TempInfo> getTemperatures(String town ) {
        return subscriber ->
                subscriber.onSubscribe(
                new TempSubscription( subscriber, town ) );
    }

    public static Publisher<TempInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe( subscriber );
            processor.onSubscribe( new TempSubscription(processor, town) );
        };
    }
}
