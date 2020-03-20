package utils;

import java.io.*;
import java.net.URL;

public class HttpRequester extends AbstractRequester {
    /**
     * The implementation in HttpRequester just does an
     * http request and return the response stream
     * @param path
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getStream(String path) throws IOException {
         return new URL(path).openStream();

    }


}
