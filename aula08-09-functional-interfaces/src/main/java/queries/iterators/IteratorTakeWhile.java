package queries.iterators;


import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;

public class IteratorTakeWhile<T> implements Iterator<T> {
    private Iterator<T> src;
    private Predicate<T> p;
    Optional<T> box;
    boolean done;


    public IteratorTakeWhile(Iterable<T> src, Predicate<T> p) {
        this.p=p;
        this.src = src.iterator();
        box = Optional.empty();
    }


    @Override
    public boolean hasNext() {
        if (box.isPresent()) return true;
        if (done || !src.hasNext()) return false;
        T item = src.next();
        if (!p.test(item)) { done=true; return false; }
        box = Optional.of(item);
        return true;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        T t = box.get();
        box = Optional.empty();
        return t;
    }
}
