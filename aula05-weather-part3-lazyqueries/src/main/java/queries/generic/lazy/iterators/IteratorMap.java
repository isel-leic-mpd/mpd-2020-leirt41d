package queries.generic.lazy.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;

public class IteratorMap<T,U>  implements Iterator<U> {
    private final Iterator<T> srcIter;
    private final Function<T,U> mapper;

    public IteratorMap(Iterable<T> src, Function<T,U> mapper) {
        this.srcIter = src.iterator();
        this.mapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return srcIter.hasNext();
    }

    @Override
    public U next() {
        if (!hasNext())
            throw new NoSuchElementException();

        return mapper.apply(srcIter.next());
    }
}
