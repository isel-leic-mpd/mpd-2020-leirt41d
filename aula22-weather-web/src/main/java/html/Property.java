package html;

import java.io.IOException;
import java.io.Writer;

public class Property {
    private String name;
    private String value;

    public Property(String name, String value) {
        this.name =name; this.value = value;
    }

    public String getName()  { return name; }
    public String getValue() { return value;}

    protected  void showOn(Writer w, int indentLevel) throws IOException {
        Element.indent(w, indentLevel);
        w.write(String.format("%s:%s;\n", name,value));
    }
}
