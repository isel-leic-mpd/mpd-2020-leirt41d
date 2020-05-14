package utils.spliterators;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class SpliteratorCollapse1<T>
        extends Spliterators.AbstractSpliterator<T>
        implements Consumer<T> {

    private final Spliterator<T> source;

    public SpliteratorCollapse1(Spliterator<T> source) {
        super(Long.MAX_VALUE, source.characteristics());
        this.source = source;
    }

    private T curr = null;

    public boolean tryAdvance(Consumer<? super T> action) {
        T prev = curr;
        boolean hasNext;
        while (
                (hasNext = source.tryAdvance(t ->
                        curr=t)) && curr.equals(prev)) {}
        if(hasNext) action.accept(curr);
        return hasNext;
    }

    /// JM Para quê esta implementação de Consumer??
    public void accept(T item) {
        curr = item;
    }
}



