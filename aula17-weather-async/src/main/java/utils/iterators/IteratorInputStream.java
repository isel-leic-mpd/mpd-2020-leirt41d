package utils.iterators;

import java.io.*;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class IteratorInputStream implements Iterator<String> {
    private BufferedReader reader;
    private boolean done;
    private String line;

    public IteratorInputStream(InputStream input) {
        this.reader = new BufferedReader(new InputStreamReader(input));
    }

    @Override
    public boolean hasNext() {
        if (done) return false;
        if (line != null) return true;
        try {
            line = reader.readLine();
            if (line == null) {
                done = true;
                reader.close();
                return false;
            }
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
        return true;
    }

    @Override
    public String next() {
        if (!hasNext()) throw new NoSuchElementException();
        String l = line;
        line = null;
        return l;
    }
}
