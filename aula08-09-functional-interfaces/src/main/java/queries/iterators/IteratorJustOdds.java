package queries.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;


public class IteratorJustOdds<T> implements Iterator<T> {
    private final Iterator<T> src;
    Optional<T> box;

    public IteratorJustOdds(Iterable<T> src ) {
        this.src = src.iterator();
        box = Optional.empty();
    }

    @Override
    public boolean hasNext() {
        if (box.isPresent()) return true;
        if (!src.hasNext()) return false;
        box = Optional.of(src.next());
        if (src.hasNext()) src.next(); // consume even item
        return true;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        T item = box.get();
        box = Optional.empty();
        return item;
    }
}
