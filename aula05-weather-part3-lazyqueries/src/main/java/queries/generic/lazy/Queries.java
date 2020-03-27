package queries.generic.lazy;

import queries.generic.lazy.iterators.IteratorDropWhile;
import queries.generic.lazy.iterators.IteratorFilter;
import queries.generic.lazy.iterators.IteratorMap;
import queries.generic.lazy.iterators.IteratorSkip;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class Queries {

    // Creators ou Factories

    public static <T> Iterable<T> iterate (
            T seed,
            UnaryOperator<T> func) {
        return () -> new Iterator<T>() {
            T next = seed;
            public boolean hasNext() {
                return true;
            }

            public T next() {
                T curr = next;
                next = func.apply(next);
                return curr;
            }
        };
    }

    @SafeVarargs
    public static  <T> Iterable<T> of( T... params) {
        // Modify to a lazy implementation
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

    public static <T> Iterable<T> takeWhile(Iterable<T> src, Predicate<T>  f) {
        // TO Complete!
        return null;
    }




    public static <T> Iterable<T> limit(Iterable<T> src, long maxSize) {// To implement
        //return null;


        return () -> new Iterator<T>() {
            long n = 0;
            Iterator<T> srcIter = src.iterator();

            @Override
            public boolean hasNext() {
                return (n < maxSize && srcIter.hasNext());
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                n++;
                return srcIter.next();
            }
        };
    }



    public static <T> Iterable<T> dropWhile(Iterable<T> src, Predicate<T> f) {
        return () -> new IteratorDropWhile<>(src, f);
    }

    public static <T> Iterable<T> justEvens(Iterable<T> src) {

        return null;
    }

    public static <T> Iterable<T> justOdds(Iterable<T> src) {

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
        R res = null;
        // TO COMPLETE!
        return res;
    }

    public static <T> List<T> toList(Iterable<T> src)  {
        List<T> result = new ArrayList<>();

        // TO COMPLETE!
        return result;
    }

}
