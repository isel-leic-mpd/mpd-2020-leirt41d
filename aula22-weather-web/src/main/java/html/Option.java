package html;

public class Option extends Element {
    public Option(String text, String value) {
        super("option", new Text(text));
        addAtribute("value", value);
    }
}
