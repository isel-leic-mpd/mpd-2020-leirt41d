package queries;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Sequence<T> {
    boolean tryAdvance(Consumer<T> action);

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
        return (Consumer<T> action) -> {
            // a implementar
            return false;
        };

    }

    default Optional<T> first() {
        // a implementar
        return Optional.empty();
    }
}
