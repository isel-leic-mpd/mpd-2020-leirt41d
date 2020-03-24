package queries.generic.lazy;

import queries.generic.lazy.iterators.IteratorFilter;
import queries.generic.lazy.iterators.IteratorMap;
import queries.generic.lazy.iterators.IteratorSkip;
import utils.Filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class Queries {
    // Creators

    @SafeVarargs
    public static  <T> Iterable<T> of( T... params) {
        return Arrays.asList(params);
    }

    private static class OddsIterator implements Iterator<Integer> {
        int curr = 1;

        @Override
        public boolean hasNext() {

            return true;
        }

        @Override
        public Integer next() {
            int ret = curr;
            curr = curr+2;
            return ret;
        }
    }

    public static Iterable<Integer> odds() {
        return () -> new OddsIterator();
    }

    // intermediate operations

    public static <T, U> Iterable<U> map(
            Iterable<T> src,
            Function<T, U> mapper) {

        return  () -> new IteratorMap<>(src, mapper);
    }

    public static <T> Iterable<T> filter(
            Iterable<T> src,
            Predicate<T> f) {
        return () -> new IteratorFilter<>(src, f);
    }

    public static <T> Iterable<T> skip(Iterable<T> src, int n) {
       // return () -> new IteratorSkip<>(src, n);
        Iterator<T> iter = src.iterator();
        while(n > 0 && iter.hasNext()) {
            iter.next();
            --n;
        }
        return () -> iter;
    }

    public static <T> Iterable<T> takeWhile(Iterable<T> src, Filter<T>  f) {
        // TO Complete!
        return null;
    }

    // terminal operations

    public static <T> int count(Iterable<T> src) {
        // TO Complete!
        return 0;
    }

    public static <T> T first(Iterable<T> src) {
        // TO Complete!
        return null;
    }

    public static <T,R> R reduce(
            Iterable<T> src, R initial,
            BiFunction<R,T,R> accum) {
        // TO Complete!
        return null;
    }
}
