package html;

public class Label  extends Element {
    public Label(String name, String text, String type ) {

        super("Label",  new Text(text), Input.createFromType(name, type));
    }

    public Label(String name, String text ) {
        this(name, text , "text");
    }
}
