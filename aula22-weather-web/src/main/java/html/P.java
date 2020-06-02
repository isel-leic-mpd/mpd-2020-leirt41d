package html;

public class P extends Element {
    private static final String NAME="p";

    public P(Node... childs) {
        super(NAME, childs);
    }

    public P(String text) {
        super(NAME, new Text(text));
    }
}
