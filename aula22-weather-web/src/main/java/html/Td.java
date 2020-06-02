package html;

public class Td extends Element {
    private static final String NAME="td";

    public Td(Node... childs) {
        super(NAME, childs);
    }

    public Td(String text) {
        super(NAME, new Text(text));
    }

    public Td(Integer num) {
        super(NAME, new Text(num.toString()));
    }
}
