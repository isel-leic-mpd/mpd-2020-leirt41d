package utils;
import utils.spliterators.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StreamUtilsAula13 {

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

    public static <T> Stream<T> collapse1(Stream<T> src) {
        return StreamSupport.stream(
            new SpliteratorCollapse1<T>(src.spliterator()), false);
    }

    public static <T> Stream<T> collapse2(Stream<T> src) {
        return StreamSupport.stream(
        new SpliteratorCollapse2<>(src, Optional.empty()), false);
    }

    public static <T> Stream<T> collapse3(Stream<T> src) {
        return StreamSupport.stream(
                new SpliteratorCollapse3<T>(src.spliterator()), false);
    }

    public static <T> Supplier<Stream<T>>
        cache( Stream<T> src) {
        return null;
    };
}
