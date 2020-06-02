package html;

public class Head extends Element {
    private static final String NAME="head";

    public Head(Node... childs) {
        super(NAME, childs);
    }
}
