package html;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import static html.Node.indent;

public class Rule {
    private List<Property> properties;
    private List<String> targets;

    public Rule(String targets, Property ... properties) {
        //pre-conditions

        String[] tgs = targets.split(",");
        if (tgs.length == 0) throw new IllegalArgumentException("Rule with invalid target set");
        if (properties.length == 0) throw new IllegalArgumentException("Rule with invalid properties set");
        this.properties = new ArrayList<>();
        this.targets = new ArrayList<>();
        for(Property prop: properties) this.properties.add(prop);

        for(String t : tgs) this.targets.add(t);
    }

    public  void showOn(Writer w, int indentLevel) throws IOException{
        indent(w, indentLevel);

        w.write(targets.get(0));

        for(int i=1; i < targets.size();++i)
            w.write(String.format(", %s", targets.get(i)));
        w.write( " {" + System.lineSeparator());
        for(Property p: properties)
            p.showOn(w, indentLevel+1);
        indent(w, indentLevel);
        w.write("}" + System.lineSeparator());
    }
}
