package flowtemps;


import static flowtemps.TempPublisher.getTemperatures;

public class Main {

    public static void main( String[] args ) {
        getTemperatures( "New York" )
                .subscribe( new TempSubscriber() );
    }
}

