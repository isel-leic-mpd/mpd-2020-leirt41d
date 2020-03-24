package utils;

import java.io.InputStream;
import java.io.Reader;

public interface IRequest {
    Iterable<String> getContent(String path);
    InputStream openStream(String path);
}
