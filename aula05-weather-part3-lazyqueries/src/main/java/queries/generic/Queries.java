package queries.generic;

import dto.WeatherInfo;
import utils.Filter;
import utils.WeatherInfoToDouble;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Queries {

    /// the generic version of filter.
    // Now we can filter a sequence of any type!
    public static <T> Iterable<T> filter(
            Iterable<T> src,
            Filter<T> f) {
        List<T> result = new ArrayList<>();
        for(T wi : src) {
            if (f.test(wi))
                result.add(wi);
        }
        return result;
    }

    public static <T, U> Iterable<U> map(
            Iterable<T> src,
            Function<T, U> mapper) {
        List<U> result = new ArrayList<>();
        for(T t : src) {
            result.add(mapper.apply(t));
        }
        return result;
    }

}
