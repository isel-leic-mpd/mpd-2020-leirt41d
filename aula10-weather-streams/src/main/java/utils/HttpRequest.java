package utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;

public class HttpRequest extends AbstractRequest {
    @Override
    public InputStream getStream(String path)  {
        try {
           URL url = new URL(path);

           return url.openStream();
        }
        catch(IOException e) {
           throw new UncheckedIOException(e);
        }
    }


}
