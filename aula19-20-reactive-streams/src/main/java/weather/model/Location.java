package weather.model;

import io.reactivex.Observable;
import weather.utils.TriFunction;

import java.time.LocalDate;
import java.util.concurrent.CompletableFuture;

import java.util.stream.Stream;

public class Location {


    private String name;
    private String country;
    private double latitude;
    private double longitude;

    private TriFunction<Location,
                        LocalDate,
                        LocalDate,
                        Observable<DayInfo>> pastDays;

    public static Location of(double latitude, double longitude) {
        return new Location(null,null,latitude,longitude,null);
    }


    public Location(String name,
                    String country,   double latitude,
                    double longitude,
                    TriFunction<Location,
                                LocalDate,
                                LocalDate,
                                Observable<DayInfo>> pastDays) {
        this.name = name;
        this.country = country;

        this.latitude = latitude;
        this.longitude = longitude;
        this.pastDays = pastDays;
    }

    // acessors
    public String getName()         { return name; }
    public String getCountry()      { return country; }
    public double getLatitude()     { return latitude; }
    public double getLongitude()    { return longitude; }


    public Observable<DayInfo>
    getDays(LocalDate from, LocalDate to)  {

        return pastDays.apply(this, from,to);
    }

    @Override
    public String toString() {
        return "{"
                + name
                + ", country=" + country
                + ", latitude=" + latitude
                + ", longitude=" + longitude
                + "}";
    }


}
