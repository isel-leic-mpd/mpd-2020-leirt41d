package html;

public class Span extends Element{
    private static final String NAME="span";

    public Span(Node... childs) {
        super(NAME, childs);
    }
}
