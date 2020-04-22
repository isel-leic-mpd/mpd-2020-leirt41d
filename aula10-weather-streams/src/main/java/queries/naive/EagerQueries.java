package queries.naive;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class EagerQueries {

    // intermediate eager operations
    public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> p) {
        List<T> res = new ArrayList<>();
        for(T item: src) {
            if (p.test(item)) res.add(item);
        }
        return res;
    }

    public static <T,U> Iterable<U> map(Iterable<T> src, Function<T,U> mapper ) {
        List<U> res = new ArrayList<>();
        for(T item: src) {
            res.add(mapper.apply(item));
        }
        return res;
    }

    // final operations
    public static <T> long  count(Iterable<T> src) {
        long res =0;
        for(T item: src) {
            res++;
        }
        return res;
    }

    public static <T,U> U reduce(Iterable<T> src, U initial, BiFunction<U,T,U> accum) {
        U res = initial;
        for(T item: src) {
            res = accum.apply(res, item);
        }
        return res;
    }

}
