package utils.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class IteratorFlatMap<T,R> implements Iterator<R> {
    private final Function<T, Iterable<R>> mapper;
    private final Iterator<T> srcIter;
    private Iterator<R> currIter;

    Optional<R> curr;

    public IteratorFlatMap(Iterable<T> src,
                           Function<T, Iterable<R>> mapper) {
        this.srcIter = src.iterator();
        this.mapper = mapper;
        curr = Optional.empty();
    }

    @Override
    public boolean hasNext() {
        if (curr.isPresent()) return true;
        while (currIter == null || !currIter.hasNext()) {
            if (!srcIter.hasNext()) return false;
            currIter = mapper.apply(srcIter.next()).iterator();
        }
        curr = Optional.of(currIter.next());
        return true;
    }

    @Override
    public R next() {
        if (!hasNext())
            throw new NoSuchElementException();
        R n = curr.get();
        curr = Optional.empty();
        return n;
    }
}