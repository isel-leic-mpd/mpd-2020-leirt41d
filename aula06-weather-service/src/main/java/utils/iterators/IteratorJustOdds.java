package utils.iterators;

import utils.Box;


import java.util.Iterator;
import java.util.NoSuchElementException;


public class IteratorJustOdds<T> implements Iterator<T> {
    private final Iterator<T> src;
    Box<T> box;

    public IteratorJustOdds(Iterable<T> src ) {
        this.src = src.iterator();
        box = Box.empty();
    }

    @Override
    public boolean hasNext() {
        if (box.isPresent()) return true;
        if (!src.hasNext()) return false;
        box = Box.of(src.next());
        if (src.hasNext()) src.next(); // consume even item
        return true;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        T item = box.getItem();
        box = Box.empty();
        return item;
    }
}
