package queries.miterators;

import queries.MIterable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Function;

public class IteratorFlatMap<T,R> implements MIterator<R> {
    private final Function<T, MIterable<R>> mapper;
    private final MIterator<T> srcIter;
    private MIterator<R> currIter;

    Optional<R> curr;

    public IteratorFlatMap(MIterable<T> src,
                           Function<T, MIterable<R>> mapper) {
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