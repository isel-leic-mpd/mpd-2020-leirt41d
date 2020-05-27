package flowtemps;

import java.util.concurrent.Flow;

import static flowtemps.TempPublisher.getCelsiusTemperatures;

public class MainComposed {
    public static void main( String[] args ) {
        getCelsiusTemperatures( "New York" )
                .subscribe( new TempSubscriber() );
    }
}
