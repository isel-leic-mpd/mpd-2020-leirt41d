package queries.generic.lazy.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class IteratorDropWhile<T> implements
        Iterator<T> {

    private final Iterator<T> srcIter;
    private final Predicate<T> pred;
    private T curr;
    private boolean hasAdvanced;

    public IteratorDropWhile(
            Iterable<T> src, Predicate<T> pred) {
        this.srcIter = src.iterator();
        this.pred = pred;

        while ( srcIter.hasNext() ) {
           curr = srcIter.next();
           if (!pred.test(curr)) {
               hasAdvanced = true;
               return;
           }
        }
    }


    @Override
    public boolean hasNext() {
       if (hasAdvanced) return true;
       return srcIter.hasNext();
    }

    @Override
    public T next() {
        if (!hasNext())
            throw new NoSuchElementException();
        if (hasAdvanced) {
            hasAdvanced = false;
            return curr;
        }
        return srcIter.next();
    }
}
