package queries;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Sequence<T> {

    /**
     * Functional version of iteration interface
     * tryAdvance receives the consumer which is called
     * with the next element of the sequence, if one exists
     * @param action - consumer
     * @return true if the consumer was called with the next element
     *              or false if none exists
     */
    boolean tryAdvance(Consumer<T> action);

    /**
     * returns a new sequence with the elements from
     * the Iterable received
     * @param src the elements source
     * @param <T>
     * @return
     */
    static <T> Sequence<T> from(Iterable<T> src) {
        Iterator<T> it = src.iterator();
        return action -> {
           if (!it.hasNext()) return false;
           action.accept(it.next());
           return true;
        };
    }

    default <R> Sequence<R> map(Function<T,R> mapper) {
        return (Consumer<R> action) ->
                this.tryAdvance(t -> action.accept(mapper.apply(t)) );
    }

    default Sequence<T> filter(Predicate<T> pred) {
        return action -> {
            boolean[] found = {false};
            while(tryAdvance(t -> {
                if (pred.test(t)) {
                    action.accept(t);
                    found[0] = true;
                }
            }) && !found[0]);
            return found[0];
        };

    }

    default <R> Sequence<R> flatMap(Function<T, Sequence<R>> mapper) {
        Sequence<R>[] seqr = new Sequence[1];
        return action -> {
            while (seqr[0] == null || !seqr[0].tryAdvance(action))  {
                if (!tryAdvance(t -> seqr[0] = mapper.apply(t))) return false;
            }
            return true;
        };
    }

    default Sequence<T> skip(int n) {
        int[] ndiscard = {n};
        return action -> {
            while(ndiscard[0] > 0 && this.tryAdvance(__ -> {})) --ndiscard[0];
            return tryAdvance(action);
        };
    }

    default Sequence<T> skip2(int n) {
        while(n > 0 && this.tryAdvance(__ -> {})) --n;
        return this; //action -> tryAdvance(action);
    }

    // operações terminais

    // In order to avoid the java limitation
    // that the captured variables must be final,
    // the state captured could be wrapped in an object,
    // but generally an array or list can be used has illustrated
    // in the "first" operation
    static class Wrapper<T> {
        public T val;
    }

    default Optional<T> first() {
        //Wrapper<T> wrapper = new Wrapper<>();
        Object[] val = new Object[1];

        if (!tryAdvance(t ->  val[0] = t)) return Optional.empty();
        return Optional.of((T) val[0]);
    }


    default List<T> toList() {
        List<T> list = new ArrayList<>();

        while(tryAdvance(t -> list.add(t)));

        return list;
    }

}
