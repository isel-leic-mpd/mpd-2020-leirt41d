import dto.DayInfo;
import dto.Location;
import dto.WeatherInfo;
import utils.IRequest;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class WeatherWebApi {
    private static final String API_KEY;
    private static final String WEATHER_SERVICE =  "http://api.worldweatheronline.com/premium/v1/";
    private static final String WEATHER_PAST_TEMPLATE =
            "past-weather.ashx?q=%s&date=%s&enddate=%s&tp=%d&format=csv&key=%s";
    private static final String WEATHER_SEARCH_TEMPLATE = "search.ashx?query=%s&format=tab&key=%s";

    private static String getApiKeyFromResources() {
        try {
            URL keyFile = ClassLoader.getSystemResource("worldweatheronline-app-key.txt");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(keyFile.openStream()))) {
                return reader.readLine();
            }

        }
        catch(IOException e) {
            throw new IllegalStateException(
                    "YOU MUST GET a KEY from developer.worldweatheronline.com and place it in src/{main | test}/resources/worldweatheronline-app-key.txt");
        }
    }

    static {
        API_KEY = getApiKeyFromResources();
    }

    // the object to request response input stream
    private IRequest req;

    /**
     * the IRequest object received in constructor abstracts WeatherWebApi
     * from knowledge about how to access the data source.
     * We say we have a CONSTRUCTOR DEPENDENCY INJECTION since the
     * way to get the data is "injected via constructor"
     * @param req
     */
    public WeatherWebApi( IRequest req) {
        this.req = req;
    }


    /**
     * The operation used to to get past weather info given a date interval.
     * Note that we put an aditional parameter to specify the acquisition period
     * @param latitude
     * @param longitude
     * @param from
     * @param to
     * @param period
     * @return
     */
    public Iterable<WeatherInfo> pastWeather( double latitude, double longitude, LocalDate from, LocalDate to, int period) {
        String query = latitude + "," + longitude;
        String path =  WEATHER_SERVICE + String.format(WEATHER_PAST_TEMPLATE, query, from, to, period,API_KEY);

        List<WeatherInfo> result = new ArrayList<>(); // where the dto.WeatherInfo instances are collected

        /*
        // the old way
        try {
            // used to do the HTTP request to worldweatheronline service

            URL url = new URL(path);
            InputStream input = url.openStream();
            try(BufferedReader reader =
                        new BufferedReader(new InputStreamReader(input))) {
                String line;
                // read the initial lines with a comment
                while((line= reader.readLine()) != null && line.startsWith("#"));

                //read lines in alternation to skip day lines
                boolean toRead= false;
                while((line= reader.readLine()) != null) {
                    if (toRead) {
                        result.add(WeatherInfo.valueOf(line));
                        toRead = false;
                    }
                    else {
                        toRead = true;
                    }
                }
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
        */


        // the new way
        Iterable<String> lines = req.getContent(path);

        Iterator<String> it = lines.iterator();
        String line;

        while(it.hasNext()  ) {
            line = it.next();
            if (!line.startsWith("#")) break;
        }

        boolean toRead = false;
        while(it.hasNext()  ) {
            line = it.next();
            if (toRead) {
                result.add(WeatherInfo.valueOf(line));
                toRead = false;
            }
            else {
                toRead = true;
            }
        }
        return result;
    }

    /**
     * The operation used to obtain general daily info given a date interval
     * @param latitude
     * @param longitude
     * @param from
     * @param to
     * @return
     */
    public Iterable<DayInfo> pastDays(double latitude, double longitude, LocalDate from, LocalDate to) {
        String query = latitude + "," + longitude;
        String path =  WEATHER_SERVICE + String.format(WEATHER_PAST_TEMPLATE, query, from, to, 24, API_KEY);

        List<DayInfo> result = new ArrayList<>(); // where the dto.WeatherInfo instances are collected
        Iterable<String> src = req.getContent(path);

        // TO COMPLETE

        return result;
    }

    /**
     * The operation used to obtain location info given the name of the location
     * @param location
     * @return
     */
    public Iterable<Location> search(String location) {
        String path =  WEATHER_SERVICE + String.format(WEATHER_SEARCH_TEMPLATE, location, API_KEY);

        List<Location> result = new ArrayList<>(); // where the dto.WeatherInfo instances are collected

        Iterable<String> src = req.getContent(path);

        // TO COMPLETE
        return result;

    }

}
