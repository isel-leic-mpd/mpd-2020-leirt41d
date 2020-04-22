package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

public class FileRequest  extends AbstractRequest {
    @Override
    public InputStream getStream(String path)  {

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
