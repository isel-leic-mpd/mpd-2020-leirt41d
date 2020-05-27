package weather.utils;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    public  static <T, U, R> Stream<R>
    zip(Stream<T> src1, Stream<U> src2, BiFunction<T,U,R> combiner) {
        return
                StreamSupport.stream(new SpliteratorZip<>(
                        src1.spliterator(), src2.spliterator(), combiner), false
                );
    }

    private static class SpliteratorZip<T, U, R> extends Spliterators.AbstractSpliterator<R> {

        private final Spliterator<T> src1;
        private final Spliterator<U> src2;
        BiFunction<T, U, R> combiner;

        /**
         * zip characteristics are the intersection of src1 and src2 characteristics
         * but SORTED and DISTINCT can't be guaranteed by zip operation.
         *
         * @param src1
         * @param src2
         * @return the zip charcteristics given sources characteristics
         */
        private static <T, U> int zipCharaceristics(Spliterator<T> src1, Spliterator<U> src2) {
            return src1.characteristics() & src2.characteristics() &
                    ~(DISTINCT & SORTED);
        }

        public SpliteratorZip(Spliterator<T> src1, Spliterator<U> src2,
                              BiFunction<T, U, R> combiner) {
            super(Math.min(src1.estimateSize(), src2.estimateSize()),
                    zipCharaceristics(src1, src2));

            this.src1 = src1;
            this.src2 = src2;
            this.combiner = combiner;
        }

        @Override
        public boolean tryAdvance(Consumer<? super R> action) {
            boolean[] oneMore = {false};
            src1.tryAdvance(t -> {
                src2.tryAdvance(u -> {
                    R r = combiner.apply(t, u);
                    action.accept(r);
                    oneMore[0] = true;
                });
            });
            return oneMore[0];
        }
    }
}
