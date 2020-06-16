package repo.functions;

import java.util.function.Function;

public interface MyBiFunction<T,U,R> {
    R apply(T t, U u);

    // add the default method curry here
}

