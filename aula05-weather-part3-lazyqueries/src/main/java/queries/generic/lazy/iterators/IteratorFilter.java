package queries.generic.lazy.iterators;

import utils.Filter;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class IteratorFilter<T> implements Iterator<T> {
    private final Iterator<T> srcIter;
    private final Predicate<T> f;
    T curr;
    boolean hasAdvanced;

    public IteratorFilter( Iterable<T> src, Predicate<T> f) {
        this.srcIter = src.iterator();
        this.f = f;
    }

    @Override
    public boolean hasNext() {
        if (hasAdvanced) return true;
        while (srcIter.hasNext()) {
            curr = srcIter.next();
            if (f.test(curr))  {
                hasAdvanced = true;
                return true;
            }
        }
        return false;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        hasAdvanced = false;
        return curr;
    }
}
