package dto;


public class LocationDto {
    /*
    #The Search API, comma(tab) separated values (csv)
    #Data returned is laid out in the following order:-
    #AreaName    Country     Region(If available)    Latitude    Longitude   Population(if available)    Weather Forecast URL
    #
    Lisbon	Portugal	Lisboa	38.717	-9.133	517798	http://api.worldweatheronline.com/v2/weather.aspx?q=38.7167,-9.1333
    Lisbon	United States of America	Maine	44.031	-70.105	9392	http://api.worldweatheronline.com/v2/weather.aspx?q=44.0314,-70.105
    */



    public static class NameDto {
        private String value;

        public NameDto(String value ) { this.value =value; }
    }
    // indexes
    private static final int NAME       =   0;
    private static final int COUNTRY    =   1;
    private static final int LATITUDE   =   3;
    private static final int LONGITUDE  =   4;
    private static final int WEATHER_URL=   5;


    private NameDto[] name;        // index 0
    private NameDto[] country;     // index 1
    private double latitude;       // index 3
    private double longitude;      // index 4
    private NameDto[] weatherUrl;  // index 5

    public LocationDto(NameDto[] name, NameDto[] country,  double latitude,
                       double longitude, NameDto[] weatherUrl) {
        this.name = name;
        this.country = country;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherUrl = weatherUrl;
    }

    public LocationDto(String name, String country,  double latitude,
                       double longitude, String weatherUrl) {
        NameDto[] name_a = {new NameDto(name)};
        NameDto[] country_a = {new NameDto(country)};
        NameDto[] weatherUrl_a = {new NameDto(weatherUrl)};

        this.name = name_a;
        this.country = country_a;
        this.latitude = latitude;
        this.longitude = longitude;
        this.weatherUrl = weatherUrl_a;
    }

    // acessors
    public String getName()         { return name[0].value; }
    public String getCountry()      { return country[0].value; }
    public double getLatitude()     { return latitude; }
    public double getLongitude()    { return longitude; }
    public String getWeatherUrl()   { return weatherUrl[0].value; }

    @Override
    public String toString() {
        return "{"
                + name
                + ", country=" + country
                + ", latitude=" + latitude
                + ", longitude=" + longitude
                + ", weatherUrl=" + weatherUrl;
    }

    public static LocationDto valueOf(String line) {
        String[] parts = line.split("\t");

        return new LocationDto(
                parts[NAME],
                parts[COUNTRY],
                Double.valueOf(parts[LATITUDE]),
                Double.valueOf(parts[LONGITUDE]),
                parts[WEATHER_URL]
        );
    }
}
