package interfaces;

import java.util.function.Function;

public interface MComparator<T> {
    int compare(T t1, T t2);

    static <T, U extends Comparable<U>>
    MComparator<T> comparing(Function<T, U> key) {
        return (t1, t2 ) -> {
            U u1 = key.apply(t1);
            U u2 = key.apply(t2);
            return u1.compareTo(u2);
        };
    }

    static <T extends Comparable<T>> MComparator<T>
    reverseOrder() {
        return (t1,t2) -> t2.compareTo(t1);
    }

    static <T, U> MComparator<T>
    comparing(Function<T, U> key, MComparator<U> keyComp) {
        return (t1, t2) -> {
            U u1 = key.apply(t1);
            U u2 = key.apply(t2);
            return keyComp.compare(u1,u2);
        };
    }

    default MComparator<T> reversed() {
        return (t1,t2) -> compare(t2, t1);
    }


    default MComparator<T> thenComparing(MComparator<T> other) {
        return (t1, t2) -> {
            int res = compare(t1,t2);
            if (res!= 0) return res;
            return other.compare(t1,t2);
        };
    }


    default <U extends Comparable<U>> MComparator<T>
    thenComparing(Function<T,U> key) {
        /*
        return (t1, t2) -> {
            int res = compare(t1,t2);
            if (res!= 0) return res;
            return key.apply(t1).compareTo(key.apply(t2));
        };
        */
        return thenComparing(comparing(key));
    }


}
