import dto.WeatherInfo;

import java.util.ArrayList;
import java.util.List;

public class WeatherQueries {
    // first attempt, naive queries

    public static Iterable<WeatherInfo> getSunnyDays(Iterable<WeatherInfo> src) {
        List<WeatherInfo> result = new ArrayList<>();
        for(WeatherInfo wi : src) {
            if (wi.getDescription().contains("Sunny"))
                result.add(wi);
        }
        return result;
    }

    public static Iterable<WeatherInfo> getCloudyDays(Iterable<WeatherInfo> src) {
        List<WeatherInfo> result = new ArrayList<>();
        for(WeatherInfo wi : src) {
            if (wi.getDescription().contains("cloudy"))
                result.add(wi);
        }
        return result;
    }

    // second attempt, value parametrization

    public static Iterable<WeatherInfo> getDaysAs(Iterable<WeatherInfo> src,
                                           String keyDescription) {
        List<WeatherInfo> result = new ArrayList<>();
        for(WeatherInfo wi : src) {
            if (wi.getDescription().contains(keyDescription))
                result.add(wi);
        }
        return result;
    }

    // third attempt, behaviour parametrization






}
