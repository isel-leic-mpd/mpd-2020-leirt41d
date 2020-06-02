package html;

public class Li extends Element{
    private static final String NAME="li";

    public Li(Node... childs) {
        super(NAME, childs);
    }

    public Li(String text) {
        super(NAME, new Text(text));
    }
}
