package weather.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public interface AsyncRequest {
    CompletableFuture<Stream<String>>
    getContent(String path);

    static  Stream<String> getLines(InputStream input) {
        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(input));
        return reader.lines();
    }
}
