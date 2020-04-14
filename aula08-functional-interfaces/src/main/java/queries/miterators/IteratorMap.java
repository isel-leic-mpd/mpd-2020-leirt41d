package queries.miterators;

import queries.MIterable;
import queries.miterators.MIterator;

import java.util.Iterator;
import java.util.function.Function;

public class IteratorMap<T,U> implements MIterator<U> {
    private MIterator<T> src;
    private Function<T,U> mapper;

    @Override
    public boolean hasNext() {
        return src.hasNext();

    }

    @Override
    public U next() {
        if (!hasNext())
            throw new IllegalStateException();

        return mapper.apply(src.next());
    }


    public IteratorMap(MIterable<T> src, Function<T,U> mapper) {
        this.src = src.iterator();
        this.mapper=mapper;

    }
}
