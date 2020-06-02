package html;

import java.io.IOException;
import java.io.Writer;

public class Text extends Node {
    String text;

    public Text(String text) {
        this.text = text;
    }

    protected void writeOn(Writer w, int indentLevel) throws IOException{
        indent(w,indentLevel);
        w.write(text+System.lineSeparator());
    }
}
