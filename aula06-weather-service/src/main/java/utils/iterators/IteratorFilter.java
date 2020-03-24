package utils.iterators;

import utils.Box;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class IteratorFilter<T>  implements Iterator<T> {
    private Iterator<T> src;
    private Predicate<T> p;

    private Box<T> box;


    public IteratorFilter(Iterable<T> src, Predicate<T> p) {
        this.src = src.iterator();
        this.p=p;
        box = Box.empty();
    }

    @Override
    public boolean hasNext() {
        if (box.isPresent()) return true;
        while(src.hasNext()) {
            T item = src.next();
            if (p.test(item)) {
                box = Box.of(item);
                return true;
            }
        }
        return false;
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
