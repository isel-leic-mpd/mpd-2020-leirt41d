package utils;

import utils.spliterators.SpliteratorInputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class AbstractRequest implements Request {
    /*
    @Override
    public Stream<String> getContent(String path) {
        try {
            InputStream input = getStream(path);
            List<String> lines = new ArrayList<>(); // where the lines are collected

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                String line;
                while ((line = reader.readLine()) != null)
                    lines.add(line);
                return lines.stream();
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    */


    /**
     * A new version retrieving a lazy sequence
     * @param path
     * @return
     */
    @Override
    public Stream<String> getContent(String path) {

        return
        StreamSupport.stream(
            new SpliteratorInputStream(()->getStream(path)), false);
    }


}
