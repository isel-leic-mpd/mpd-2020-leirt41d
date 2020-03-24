package utils.iterators;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Function;

public class IteratorFlatMap<T,R> implements Iterator<R> {
    private final Function<T, Iterable<R>> mapper;
    private final Iterator<T> src;
    private Iterator<R> currIter;
    private Optional<R> currVal;

    public IteratorFlatMap(Iterable<T> src,
                           Function<T, Iterable<R>> mapper) {
        this.src = src.iterator();
        this.mapper = mapper;
        currVal = Optional.empty();
        currIter = null;
    }

    @Override
    public boolean hasNext() {
        if (currVal.isPresent()) return true;
        while (currIter == null || !currIter.hasNext()) {
            if (!src.hasNext()) return false;
            currIter = mapper.apply(src.next()).iterator();
        }
        currVal = Optional.of(currIter.next());
        return true;
    }

    @Override
    public R next() {
        if (!hasNext()) throw new IllegalStateException();

        R n = currVal.get();
        currVal = Optional.empty();
        return n;
    }
}