package queries;

import queries.miterators.*;

import java.util.*;
import java.util.function.*;

public interface MIterable<T> {

    MIterator<T> iterator();

    static <T> MIterable<T> from(T... vals) {
        return () -> new IteratorArray<>(vals);
    }

    static <T> MIterable<T> from(Iterable<T> src) {
        return () -> {
            return new MIterator<T>() {
                Iterator<T> it = src.iterator();

                @Override
                public boolean hasNext() {
                    return it.hasNext();
                }

                @Override
                public T next() {
                    return it.next();
                }
            };
        };
    }
    default MIterable<T> filter(Predicate<T> p) {

        return () -> new IteratorFilter<T>(this, p);
    }

    default <R> MIterable<R> flatMap(Function<T, MIterable<R>> mapper) {
        return () -> new IteratorFlatMap(this, mapper);
    }


    default MIterable<T> takeWhile(Predicate<T> p) {

        return () -> new IteratorTakeWhile<T>(this, p);
    }

    default <U> MIterable<U> map(Function<T, U> mapper) {
        return () -> new IteratorMap<T,U>(this, mapper);
    }

    default MIterable<T> dropWhile(Predicate<T> p) {

        return () -> new IteratorDropWhile<T>(this, p);
    }

    default   MIterable<T> skip(int n) {
        int count = n;
        MIterator<T> it =  iterator();

        while (count > 0 && it.hasNext()) { --count; it.next(); }
        return () -> it;
    }


    default  MIterable<T> justOdds() {

        return () -> new IteratorJustOdds<T>(this);
    }

    // terminal operations

    default <R> R reduce(R initial, BiFunction<R,T,R> accum) {
        R res = initial;
        MIterator<T> it = iterator();
        while(it.hasNext()) {
            T item = it.next();
            res = accum.apply(res, item);
        }
        return res;
    }

    default void forEach(Consumer<T> action) {
        MIterator<T> it = iterator();
        while(it.hasNext())
            action.accept(it.next());
    }

    default long count() {
        long c =0;
        MIterator<T> it = iterator();
        while(it.hasNext()) {
            c++;
            it.next();
        }
        return c;
    }

    default Optional<T> first() {
        MIterator<T> it = iterator();
        if (!it.hasNext()) return Optional.empty();
        return Optional.of(it.next());
    }

    /*
    public static <T> Optional<T> reduce(Iterable<T> src, BinaryOperator<T> accumulator) {
        // a implementar
        return null;
    }

    public static <T> Optional<T> max(Iterable<T> src, Comparator<T> cmp) {
        return Optional.empty();
    }

    public static <T extends Comparable<T>> Optional<T> max(Iterable<T> src) {
        return Optional.empty();
    }

    public static <T> Object[] toArray(Iterable<T> src) {
        List<T> res = new ArrayList<>();
        forEach(src, v-> res.add(v));
        return res.toArray();
    }


     */
}
