package utils.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorSkip<T> implements Iterator<T> {

    Iterator<T> srcIter;

    public IteratorSkip(Iterable<T> src, int n) {
        srcIter = src.iterator();
        while(n > 0 && srcIter.hasNext()) { srcIter.next(); --n; }
    }

    @Override
    public boolean hasNext() {
         return srcIter.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext()) throw new NoSuchElementException();
        return srcIter.next();
    }
}
