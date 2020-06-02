package html;

import java.io.IOException;
import java.io.Writer;

public class Attribute {
    private String name;
    private String value;

    public Attribute(String name, String value) {
        this.name = name; this.value = value;
    }

    public void showOn(Writer w)throws IOException {
        w.write(toString());
    }

    @Override
    public String toString() {
        return String.format(" %s=\"%s\"", name, value);
    }
}
