import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class WeatherWebApi {
    private static final String API_KEY;
    private static final String WEATHER_SERVICE =  "http://api.worldweatheronline.com/premium/v1/";
    private static final String WEATHER_PAST_TEMPLATE =
            "past-weather.ashx?q=%s&date=%s&enddate=%s&tp=24&format=csv&key=%s";
    private static final String WEATHER_SEARCH_TEMPLATE = "search.ashx?query=%s&format=tab&key=%s";

    /**
     * Retrieve API-KEY from resources
     * @return
     */
    private static String getApiKeyFromResources() {
        try {
            URL keyFile = ClassLoader.getSystemResource("worldweatheronline-app-key.txt");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
                return reader.readLine();
            }

        }
        catch(IOException e) {
            throw new IllegalStateException(
                    "YOU MUST GET a KEY from developer.worldweatheronline.com and place it in src/main/resources/worldweatheronline-app-key.txt");
        }
    }

    /**
     * Static Constructor
     */
    static {
        API_KEY = getApiKeyFromResources();
    }

    /**
     * Get WeatherInfo's from a GPS local given a date interval
     * @param latitude
     * @param longitude
     * @param from
     * @param to
     * @return
     */
    public Iterable<WeatherInfo> pastWeather( double latitude, double longitude, LocalDate from, LocalDate to) {
        String query = latitude + "," + longitude;
        String path =  WEATHER_SERVICE + String.format(WEATHER_PAST_TEMPLATE, query, from, to, API_KEY);


        List<WeatherInfo> result = new ArrayList<>(); // where the WeatherInfo instances are collected

        try {
            // used to do the HTTP request to worldweatheronline service
            URL url = new URL(path);

            // the stream used to get the response to the service request
            InputStream respStream = url.openStream();

            // TO COMPLETE!
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }

        return result;
    }

    /**
     * Get DayInfo's from a GPS local given a date interval
     * @param latitude
     * @param longitude
     * @param from
     * @param to
     * @return
     */
    public Iterable<DayInfo> pastDays( double latitude, double longitude, LocalDate from, LocalDate to) {
        String query = latitude + "," + longitude;
        String path =  WEATHER_SERVICE + String.format(WEATHER_PAST_TEMPLATE, query, from, to, API_KEY);


        List<DayInfo> result = new ArrayList<>(); // where the WeatherInfo instances are collected

        // TO COMPLETE!
        return result;
    }

    /**
     * Get local info given the name of the local
     * @param location
     * @return
     */
    public Iterable<Location> search(String location) {

        String path =  WEATHER_SERVICE + String.format(WEATHER_SEARCH_TEMPLATE, location, API_KEY);

        // TO COMPLETE!
        return null;
    }

}
