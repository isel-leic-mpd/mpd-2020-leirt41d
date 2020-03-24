package queries.generic.lazy.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class IteratorSkip<T> implements Iterator<T> {
    private final Iterator<T> srcIter;

    public IteratorSkip( Iterable<T> src, int n) {
        this.srcIter = src.iterator();
        while(n > 0 && srcIter.hasNext()) {
            srcIter.next();
            --n;
        }
    }

    @Override
    public boolean hasNext() {
        return srcIter.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        return srcIter.next();
    }
}
