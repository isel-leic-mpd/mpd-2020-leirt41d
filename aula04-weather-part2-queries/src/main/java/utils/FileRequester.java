package utils;

import java.io.*;

public class FileRequester extends AbstractRequester {

    /**
     * The implementation in FileRequester just transform the received path
     * to be conforming to a file name and get the file from the resources folder.
     * Note that the resources must be in the main folder sources or the test folder
     * sources depending if we are executing an application or running the tests.
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
