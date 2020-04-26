package utils.spliterators;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class SpliteratorZip<T, U, R> extends Spliterators.AbstractSpliterator<R> {

    private final Spliterator<T> src1;
    private final Spliterator<U> src2;
    BiFunction<T,U,R> combiner;

    public SpliteratorZip(Spliterator<T> src1, Spliterator<U> src2,
                          BiFunction<T,U,R> combiner) {
        super(Math.min(src1.estimateSize(), src2.estimateSize()),
                src1.characteristics() &
                src2.characteristics());
        this.src1 = src1;
        this.src2 = src2;
        this.combiner = combiner;
    }

    @Override
    public boolean tryAdvance(Consumer<? super R> action) {
        boolean[] oneMore={false};
        src1.tryAdvance(t -> {
                    src2.tryAdvance(u -> {
                        R r = combiner.apply(t,u);
                        action.accept(r);
                        oneMore[0] = true;
                    });
                });
        return oneMore[0];
    }
}
