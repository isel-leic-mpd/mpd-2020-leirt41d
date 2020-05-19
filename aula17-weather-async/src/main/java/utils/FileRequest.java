package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class FileRequest  implements AsyncRequest {


    @Override
    public Stream<String> getContent(String path) {
        try {
            path = path.substring(path.lastIndexOf('/') + 1, path.lastIndexOf('&'))
                    .replace('&', '-')
                    .replace('=', '-')
                    .replace('?', '-')
                    .replace(',', '-') + ".txt";


            return ClassLoader.getSystemResource(path).openStream();
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
