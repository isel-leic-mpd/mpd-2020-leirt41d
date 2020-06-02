package mappers;

import html.Element;
import weather.model.Location;

import java.time.LocalDate;
import java.time.Month;

import static html.Elements.*;

public class LocationMappers {

    public static Element mapToUnorderedList(Location loc)  {
        return ul(
                li( "Name: " +loc.getName()),
                li( "Country: " + loc.getCountry()),
                li( "Latitude: " + Double.toString(loc.getLatitude())),
                li("Longitude" + Double.toString(loc.getLongitude()))
        );
    }

    public static Element mapToTableRaw(Location l) {
        LocalDate now = LocalDate.now();
        Month month = now.getMonth().minus(1);
        int monthDays = month.length(now.isLeapYear());
        int year = now.getYear();
        LocalDate day1 = LocalDate.of(year, month.getValue(),1);
        LocalDate day2 = LocalDate.of(year, month.getValue(),monthDays);
        double latitude = l.getLatitude();
        double longitude = l.getLongitude();
        String urlTodayInfo = String.format("/weather/%f:%f/today",
                latitude, longitude, day1, day2);

        return
                tr (
                    td(l.getName()),
                    td(l.getCountry()),
                    td(Double.toString(l.getLatitude())).align("right"),
                    td(Double.toString(l.getLongitude())).align("right"),
                    td(a("Today info", urlTodayInfo))
                );
    }
}
