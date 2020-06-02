package html;

public class Table extends Element {
    private static final String NAME="table";

    public Table(Element... childs) {
        super(NAME, childs);
    }
}
