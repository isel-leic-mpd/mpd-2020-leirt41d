package utils;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileRequest  extends  AbstractRequest {

    /**
     * The implementation in FileRequest just transform the received patah to be conforming
     * to a file name and get the file from the resources folder.
     * Note the resources must be in the main folder sources or the test folder sources
     * depending we are executing an application or running the tests
     * @param path
     * @return
     * @throws IOException
     */
    @Override
    public InputStream getStream(String path) throws IOException{
        path = path.substring(path.lastIndexOf('/')+1, path.lastIndexOf('&'))
                .replace('&', '-')
                .replace('=', '-')
                .replace( '?','-')
                .replace( ',','-')+ ".txt";
        return ClassLoader.getSystemResource(path).openStream();
    }
}
