package repo.functions;

public interface MyToIntFunction<T> {
    int applyAsInt(T path);

    default MyToIntFunction<T> sumAll(MyToIntFunction<T>... others) {
        return t -> {
            int res = applyAsInt(t);
            for(MyToIntFunction<T> f : others )
                res += f.applyAsInt(t);
            return res;
        };
    }

    // add default method andThen here


}