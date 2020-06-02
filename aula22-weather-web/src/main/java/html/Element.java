package html;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public  class Element extends Node {

    private String name;
    private List<Node> childs;
    private List<Attribute> attrs;

    protected void startElement(Writer w) throws IOException{
        w.write('<');
        w.write(name);
        for (Attribute a : attrs) {
            a.showOn(w);
        }
        w.write(">" + System.lineSeparator());
    }

    protected void endElement(Writer w) throws IOException{
        w.write("</");
        w.write(name);
        w.write(">" + System.lineSeparator());
    }



    protected Element() {
    }

    protected Element(String name, Node ... childs)  {
        this.name = name;
        this.childs = new ArrayList<>();
        for(Node child: childs) this.childs.add(child);

        attrs = new ArrayList<>();
    }

    public void addAtributes(Attribute ... attrs) {

        for(Attribute a: attrs) this.attrs.add(a);
    }

    public Element addAtribute(Attribute a) {
       this.attrs.add(a);
       return this;
    }

    public Element addAtribute(String name, String value) {
        this.attrs.add(new Attribute(name,value));
        return this;
    }

    public  void writeOn(Writer w, int indentLevel)
            throws IOException{
        indent(w, indentLevel);
        startElement(w);

        for(Node child: childs) child.writeOn(w, indentLevel+1);
        indent(w, indentLevel);
        endElement(w);
    }



    public  void writeStartOn(Writer w, int indentLevel)
            throws IOException{
        indent(w, indentLevel);
        startElement(w);

        for(Node child: childs) child.writeOn(w, indentLevel+1);

    }

    public  String startText(int indentLevel) {
        StringWriter sw = new StringWriter();
        try {
            writeStartOn(sw, indentLevel);
            String s = sw.toString();
            sw.close();
            return s;
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public  void writeEndOn(Writer w, int indentLevel)
            throws IOException{
        indent(w, indentLevel);
        endElement(w);
    }

    public  String endText(int indentLevel) {
        StringWriter sw = new StringWriter();
        try {
            writeEndOn(sw, indentLevel);
            String s = sw.toString();
            sw.close();
            return s;
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public Element appendChilds(Node[] childs) {
        for(Node child: childs) this.childs.add(child);
        return this;
    }

    public Element appendChilds(List<Node> childs) {
        for(Node child: childs) this.childs.add(child);
        return this;
    }

    public void appendChild(Node child) {

        this.childs.add(child);
    }


    public String getName() {
        return name;
    }

    public Element align(String mode) {
        return addAtribute(new Attribute("align", mode));
    }
}
