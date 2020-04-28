package utils;
import utils.spliterators.SpliteratorInputStream;
import utils.spliterators.SpliteratorZip;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtils {

    private static InputStream getFileStream(String fileName) {
        try {
            return  new FileInputStream(fileName);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static Stream<String> lines(String fileName) {
        Supplier<InputStream> input = () -> getFileStream(fileName);
        SpliteratorInputStream it = new SpliteratorInputStream(input);
        return StreamSupport.stream(it , false);
    }

    public  static <T, U, R> Stream<R>
    zip(Stream<T> src1, Stream<U> src2, BiFunction<T,U,R> combiner) {
        return
        StreamSupport.stream(new SpliteratorZip<>(
                src1.spliterator(), src2.spliterator(), combiner), false
        );
    }

    public static <T> Stream<T> collapse(Stream<T> src) {
        // a completar
        return null;
    }

    public static <T> Supplier<Stream<T>>
        cache( Stream<T> src) {
        return null;
    };
}
