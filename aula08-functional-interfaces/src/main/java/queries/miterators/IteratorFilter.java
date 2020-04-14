package queries.miterators;


import queries.MIterable;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class IteratorFilter<T>  implements MIterator<T> {
    private MIterator<T> src;
    private Predicate<T> p;

    private Optional<T> box;


    public IteratorFilter(MIterable<T> src, Predicate<T> p) {
        this.src = src.iterator();
        this.p=p;
        box = Optional.empty();
    }

    @Override
    public boolean hasNext() {
        if (box.isPresent()) return true;
        while(src.hasNext()) {
            T item = src.next();
            if (p.test(item)) {
                box = Optional.of(item);
                return true;
            }
        }
        return false;
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
