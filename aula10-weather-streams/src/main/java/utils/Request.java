package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.stream.Stream;

public interface Request {
    Stream<String> getContent(String path);
    InputStream getStream(String path);
}
