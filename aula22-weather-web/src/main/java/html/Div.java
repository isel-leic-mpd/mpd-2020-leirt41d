package html;

public class Div extends Element {
    private static final String NAME="div";

    public Div(Node... childs) {
        super(NAME, childs);
    }
}
