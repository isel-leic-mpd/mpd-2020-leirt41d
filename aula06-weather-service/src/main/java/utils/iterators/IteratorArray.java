package utils.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class IteratorArray<T> implements Iterator<T> {
    private T[] vals;
    private int currIdx;

    public IteratorArray(T[] vals) {
        this.vals = vals;
        currIdx = -1;

    }
    @Override
    public boolean hasNext() {
        return currIdx < vals.length -1 ;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        return vals[++currIdx];
    }
}
