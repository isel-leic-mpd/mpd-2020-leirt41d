package html;

public class Anchor extends Element {
    public Anchor(String text, String href) {
        super("a", new Text(text));
        addAtribute("href", href);
    }
}
