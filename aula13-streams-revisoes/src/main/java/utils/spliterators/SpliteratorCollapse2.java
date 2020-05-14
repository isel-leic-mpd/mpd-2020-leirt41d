package utils.spliterators;

import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class SpliteratorCollapse2<T> extends Spliterators.AbstractSpliterator<T> {

    private Stream<T> src;
    private Optional<T> previous;
    private Spliterator<T> srcSplit;

    public SpliteratorCollapse2(Stream<T> src, Optional<T> previous){
        super(Long.MAX_VALUE, ORDERED);
        this.src = src;
        this.previous = previous;
        this.srcSplit = src.spliterator();
    }

    @Override
    public boolean tryAdvance(Consumer consumer) {
        if(!srcSplit.tryAdvance( e -> {
            if(previous.isEmpty() || !e.equals(previous.get())) {
                previous = Optional.of(e);
                consumer.accept(e);
            }
        })) {
            //src.close();
            return false;
        }
        return true;
    }
}