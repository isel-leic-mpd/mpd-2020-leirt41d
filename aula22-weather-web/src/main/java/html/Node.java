package html;

import java.io.*;

public abstract class Node {

    protected static void indent(Writer w, int indentLevel)
        throws IOException{
        StringBuilder sb = new StringBuilder();
        for(int i=0; i < indentLevel; ++i) sb.append("    ");
        w.write(sb.toString());
    }

    protected static void indent(StringBuilder sb, int indentLevel) {
        for(int i=0; i < indentLevel; ++i) sb.append("    ");
    }

    protected  abstract void writeOn(Writer w, int indentLevel)
            throws IOException;


    public  void writeOn(Writer w)throws IOException {
        writeOn(w,0);
    }


    public  String toString(int indentLevel)  {
        StringWriter sw = new StringWriter();
        try {
            writeOn(sw, indentLevel);
            String s = sw.toString();
            sw.close();
            return s;
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public  String toString()  {
        return toString(1);
    }


    public  void writeOn(OutputStream os)
        throws IOException {
        PrintWriter pw = new PrintWriter(os);
        try {
            writeOn(pw);
        }
        finally {
            pw.flush();
        }
    }
}
