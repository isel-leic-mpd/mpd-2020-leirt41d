package utils.iterators;

import utils.Box;


import java.util.Iterator;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class IteratorDropWhile<T> implements Iterator<T> {
    private Iterator<T> src;
    private Predicate<T> p;
    private boolean skipped;
    private Box<T> box;



    public IteratorDropWhile(Iterable<T> src, Predicate<T> p) {
        this.p=p;
        this.src = src.iterator();
        box = Box.empty();
    }

    @Override
    public boolean hasNext() {
        if (box.isPresent()) return true;
        if (!skipped) {
            while (!skipped && src.hasNext()) {
                T item = src.next();
                if (!p.test(item)) {
                    skipped = true;
                    box = Box.of(item);
                }
            };
            return skipped;
        }
        return src.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new IllegalStateException();
        if (box.isPresent()) {
            T item = box.getItem();
            box = Box.empty();
            return item;
        }
        return src.next();
    }
}
