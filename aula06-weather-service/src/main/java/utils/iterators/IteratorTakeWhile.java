package utils.iterators;

import utils.Box;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class IteratorTakeWhile<T> implements Iterator<T> {
    private Iterator<T> src;
    private Predicate<T> p;
    Box<T> box;
    boolean done;


    public IteratorTakeWhile(Iterable<T> src, Predicate<T> p) {
        this.p=p;
        this.src = src.iterator();
        box = Box.empty();
    }


    @Override
    public boolean hasNext() {
        if (box.isPresent()) return true;
        if (done || !src.hasNext()) return false;
        T item = src.next();
        if (!p.test(item)) { done=true; return false; }
        box = Box.of(item);
        return true;
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        T t = box.getItem();
        box = Box.empty();
        return t;
    }
}
