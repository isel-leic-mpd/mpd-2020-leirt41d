package utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractRequest implements IRequest {

    @Override
    public Iterable<String> getContent(String path) {
        try {
            InputStream input = getStream(path);
            List<String> lines = new ArrayList<>(); // where the lines are collected

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input))) {
                String line;
                while ((line = reader.readLine()) != null)
                    lines.add(line);
                return lines;
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }

    }

    @Override
    public InputStream openStream(String path) {
        try {
            return getStream(path);

        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }
    protected abstract InputStream getStream(String path) throws IOException;


}
