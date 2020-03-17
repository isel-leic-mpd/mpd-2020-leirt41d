package utils;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest extends AbstractRequest {
    /**
     * The implementation in HttpRequesr just does an HttpRequest and return the response stream
     * @param path
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getStream(String path) throws IOException {
         return new URL(path).openStream();

    }
}
