package utils;

import java.io.IOException;
import java.io.InputStream;

public class FileRequest  extends AbstractRequest {
    @Override
    protected InputStream getStream(String path) throws IOException {
        path = path.substring(path.lastIndexOf('/')+1, path.lastIndexOf('&'))
                .replace('&', '-')
                .replace('=', '-')
                .replace( '?','-')
                .replace( ',','-')+ ".txt";


        return ClassLoader.getSystemResource(path).openStream();
    }
}
