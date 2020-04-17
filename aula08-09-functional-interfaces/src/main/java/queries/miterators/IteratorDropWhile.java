package queries.miterators;


import queries.MIterable;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

public class IteratorDropWhile<T> implements MIterator<T> {
    private MIterator<T> src;
    private Predicate<T> p;
    private boolean skipped;
    private Optional<T> box;



    public IteratorDropWhile(MIterable<T> src, Predicate<T> p) {
        this.p=p;
        this.src = src.iterator();
        box = Optional.empty();
    }

    @Override
    public boolean hasNext() {
        if (box.isPresent()) return true;
        if (!skipped) {
            while (!skipped && src.hasNext()) {
                T item = src.next();
                if (!p.test(item)) {
                    skipped = true;
                    box = Optional.of(item);
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
            T item = box.get();
            box = Optional.empty();
            return item;
        }
        return src.next();
    }
}
