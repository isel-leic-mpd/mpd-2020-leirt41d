package utils.spliterators;

import java.io.*;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SpliteratorInputStream  extends
        Spliterators.AbstractSpliterator<String> {
    private final Supplier<InputStream> input;
    private BufferedReader _reader;
    private boolean done;

    private void closeReader(Reader r) {
        try {
            r.close();
        }
        catch(IOException e) {}
    }
    public SpliteratorInputStream(Supplier<InputStream> input) {
        super(Long.MAX_VALUE, ORDERED );
        this.input = input;
    }

    @Override
    public boolean tryAdvance(Consumer<? super String> action) {
        if (done) return false;
        try {
            String line = reader().readLine();
            if (line != null) {
                action.accept(line);
                return true;
            }
            done = true;
            return false;
        }
        catch(IOException e) {
            done = true;
            closeReader(reader());
            throw new UncheckedIOException(e);
        }
    }

    private BufferedReader reader() {
        if (_reader == null)
            _reader =
                new BufferedReader(new InputStreamReader(input.get()));
        return _reader;
    }
}
