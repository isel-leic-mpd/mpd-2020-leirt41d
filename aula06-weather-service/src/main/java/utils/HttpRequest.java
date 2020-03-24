package utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class HttpRequest extends AbstractRequest {
    @Override
    protected InputStream getStream(String path) throws IOException {
       URL url = new URL(path);

       return url.openStream();
    }


}
