package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Use of Template Method Pattern to have a general implementation of "getContent"
 *  given the stream obtained by the Hook Method "getStream"
 */
public abstract class AbstractRequester implements Requester {

    @Override
    public Iterable<String> getContent(String path) {
        List<String> lines = new ArrayList<>();
        try( InputStream input = getStream(path);
             InputStreamReader isr = new InputStreamReader(input);
             BufferedReader br = new BufferedReader(isr)) {

            String line;
            while((line = br.readLine()) != null)
                lines.add(line);
            return lines;
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // The Hook method to be implement by derived (concrete) classes!
    protected abstract InputStream getStream(String path) throws IOException;
}
