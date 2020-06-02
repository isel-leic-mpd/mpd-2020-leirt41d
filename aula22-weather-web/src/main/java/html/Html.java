package html;

public class Html extends Element {
    private static final String NAME="html";

    public Html(Node... childs) {
        super(NAME, childs);
    }
}
