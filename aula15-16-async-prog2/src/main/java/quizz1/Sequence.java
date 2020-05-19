package quizz1;

import java.util.function.Consumer;

public interface Sequence<T>{
    boolean tryAdvance(Consumer<T> action);

    public static <T> Sequence<T> of(T...src) {
        int[] idx = {0};
        return action -> {
            if(idx[0] >= src.length) return false;
            action.accept(src[idx[0]]);
            return idx[0]++ < src.length;
        };
    }

    public default void forEach(Consumer<T> action) {
        while(tryAdvance(action)){}
    }

    public  default Sequence<T>  concat(Sequence<T> other) {
        boolean[] done = {false};
        return action -> {
            if (done[0]) return false;
            if (tryAdvance(action)) return true;
            if (other.tryAdvance(action)) return true;
            done[0] = true;
            return false;
        };
    }

    public  default Sequence<T>  concat2(Sequence<T> other) {
        return action ->
            tryAdvance(action) || other.tryAdvance(action);
    }

}
