package queries;

import dto.WeatherInfo;
import utils.Filter;
import utils.WeatherInfoFilter;
import utils.WeatherInfoToDouble;

import java.util.ArrayList;
import java.util.List;

/*
    Tries to achieve a framework that allows generic queries (a la SQL)
    from a data source in the form of an Iterable.
 */
public class WeatherQueries {

    // first attempt, naive queries

    // this method filter the WeatherInfo sequence
    // for "sunny" days using the description field.
    // Not very generic, is just a specific filter
    public static Iterable<WeatherInfo> getSunnyDays(Iterable<WeatherInfo> src) {
        List<WeatherInfo> result = new ArrayList<>();
        for(WeatherInfo wi : src) {
            if (wi.getDescription().contains("Sunny"))
                result.add(wi);
        }
        return result;
    }

    // this method filter the WeatherInfo sequence
    // for "cloudy" days using the description field
    // Not very generic, is just a specific filter
    // very similar to the first one.
    // A method with "external iteration", i.e., a explicit sequence traverse
    // per query is definitely not the way!
    public static Iterable<WeatherInfo> getCloudyDays(Iterable<WeatherInfo> src) {
        List<WeatherInfo> result = new ArrayList<>();
        for(WeatherInfo wi : src) {
            if (wi.getDescription().contains("cloudy"))
                result.add(wi);
        }
        return result;
    }

    // second attempt, value parametrization

    // Well, in this case we pass the weather we are interested
    // But what about a query for cold days?
    // This is not the way.
    // We need a generic filter
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


    // Now it is better
    // We parametrize de filter behaviour passing
    // the concrete filter in the form of an WeatherInfoFilter
    public static  Iterable<WeatherInfo> filter(
            Iterable<WeatherInfo> src,
            WeatherInfoFilter f) {
        List<WeatherInfo> result = new ArrayList<>();
        for(WeatherInfo wi : src) {
            if (f.test(wi))
                result.add(wi);
        }
        return result;
    }


    // Another intermediary operation that allow to tarnsform
    // a sequence of WeatherInfo into a sequence of Doubles
    public static Iterable<Double> map(
            Iterable<WeatherInfo> src,
            WeatherInfoToDouble mapper) {
        List<Double> result = new ArrayList<>();
        for(WeatherInfo wi : src) {
            result.add(mapper.applyAsDouble(wi));
        }
        return result;
    }

    // our first terminal operation.
    // Adds all the elements of a sequence of doubles
    public static double sum(Iterable<Double> src) {
        double s = 0.0;
        for(Double d : src) {
            s += d;
        }
        return s;
    }

}
