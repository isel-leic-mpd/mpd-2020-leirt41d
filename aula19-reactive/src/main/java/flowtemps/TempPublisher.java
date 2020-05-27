package flowtemps;

import java.util.concurrent.Flow;

public class TempPublisher {
    public static Flow.Publisher<TempInfo> getTemperatures(String town ) {
        return subscriber -> subscriber.onSubscribe(
                new TempSubscription( subscriber, town ) );
    }

    public static Flow.Publisher<TempInfo> getCelsiusTemperatures(String town) {
        return subscriber -> {
            TempProcessor processor = new TempProcessor();
            processor.subscribe( subscriber );
            processor.onSubscribe( new TempSubscription(processor, town) );
        };
    }
}
