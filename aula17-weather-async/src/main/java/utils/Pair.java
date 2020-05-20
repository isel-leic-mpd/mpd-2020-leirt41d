package utils;

public class Pair<T1,T2> {
    public final T1 first;
    public final T2 sec;

    public Pair(T1 first, T2 sec) {
        this.first = first;
        this.sec = sec;
    }

    public String toString() {
        return String.format("(%s,%s)", first, sec);
    }

    public static <T1,T2> Pair<T1,T2> pair(T1 t1, T2 t2) {
        return new Pair(t1,t2);
    }
}
