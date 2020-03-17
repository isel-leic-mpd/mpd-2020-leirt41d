package dto;

/**
 * Data Transfer Object (DTO) to collect location info
 */
public class Location {
    /*
    #The Search API
    #Data returned is laid out in the following order:-
    #AreaName    Country     Region(If available)    Latitude    Longitude   Population(if available)    Weather Forecast URL
    #
    Lisbon	Portugal	Lisboa	38.717	-9.133	517798	http://api.worldweatheronline.com/v2/weather.aspx?q=38.7167,-9.1333
    Lisbon	United States of America	Maine	44.031	-70.105	9392	http://api.worldweatheronline.com/v2/weather.aspx?q=44.0314,-70.105
    */

    // indexes
    private static final int NAME       =   0;
    private static final int COUNTRY    =   1;
    private static final int DISTRICT   =   2;
    private static final int LATITUDE   =   3;
    private static final int LONGITUDE  =   4;
    private static final int WEATHER_URL=   5;

   // A COMPLETAR!
}
