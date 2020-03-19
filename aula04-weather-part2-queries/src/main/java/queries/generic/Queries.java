package queries.generic;

import utils.Filter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class Queries {

    /// the generic version of filter
    // now we can filter a sequence of any type!
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

  
}
