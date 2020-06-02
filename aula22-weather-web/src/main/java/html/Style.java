package html;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class Style extends Element{
    private static final String NAME="style";

    private List<Rule> rules;

    public Style(String text) {

        super(NAME, new Text(text));
    }

    public Style (Rule... rules) {
        super(NAME);
        this.rules = new ArrayList<>();
        for(Rule rule: rules) this.rules.add(rule);

    }

    public void writeOn(Writer w, int indentLevel) throws IOException
    {
        indent(w, indentLevel);
        startElement(w);

        for(Rule r: rules) {
            r.showOn(w, indentLevel+1);
        }
        indent(w, indentLevel);
        endElement(w);
    }
}
