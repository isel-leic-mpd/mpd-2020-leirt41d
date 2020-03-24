package queries.lazy;

import utils.iterators.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.*;

public class Queries {

    public static <T> Iterable<T> from(T... vals) {
        return () -> new IteratorArray<>(vals);
    }

    public static <T> Iterable<T> filter(Iterable<T> src, Predicate<T> p) {
        return () -> new IteratorFilter<T>(src, p);
    }

    public static <T,R> Iterable<R> flatMap(Iterable<T> src,
                                    Function<T,Iterable<R>> mapper) {
        return () -> new IteratorFlatMap(src, mapper);
    }


    public static <T> Iterable<T> takeWhile(Iterable<T> src,Predicate<T> p) {
        return () -> new IteratorTakeWhile<T>(src, p);
    }

    public static <T,U> Iterable<U> map(Iterable<T> src, Function<T, U> mapper) {
        return () -> new IteratorMap<T,U>(src, mapper);
    }

    public static <T> Iterable<T> dropWhile(Iterable<T> src, Predicate<T> p) {
        return () -> new IteratorDropWhile<T>(src, p);
    }

    public static <T> Iterable<T> skip(Iterable<T> src, int n) {
        int count = n;
        Iterator<T> it = src.iterator();

        while (count > 0 && it.hasNext()) { --count; it.next(); }
        return () -> it;
    }

    public static <T>  Iterable<T> justOdds(Iterable<T> src) {

        return () -> new IteratorJustOdds<T>( src);
    }

    // terminal operations

    public static <T,R> R reduce(Iterable<T> src, R initial, BiFunction<R,T,R> accum) {
        R res = initial;
        for(T item: src) {
            res = accum.apply(res, item);
        }
        return res;
    }

    public static <T> void forEach(Iterable<T> src, Consumer<T> action) {
        Iterator<T> it = src.iterator();
        while(it.hasNext())
            action.accept(it.next());
    }

    public static <T> Optional<T> first(Iterable<T> src) {
        Iterator<T> it = src.iterator();
        if (!it.hasNext()) return Optional.empty();
        return Optional.of(it.next());
    }

    public static <T> Optional<T> reduce(Iterable<T> src, BinaryOperator<T> accumulator) {
        // a implementar
        return null;
    }

    public static <T> T[] toArray(Iterable<T> src) {
         List<T> res = new ArrayList<>();
         forEach(src, v-> res.add(v));
         return (T[]) res.toArray();
    }
}
