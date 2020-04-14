package interfaces;

public interface MFunction<T,R> {

    static <T> MFunction<T,T> identity() {
        return t -> t;
    }






    R apply(T t);

    default <U> MFunction<U,R> compose(MFunction<U,T> other) {
        return u -> apply(other.apply(u));
    }

    default <U> MFunction<T,U> andThen(MFunction<R,U> other) {
        return t ->  other.apply(this.apply(t));
    }
}
