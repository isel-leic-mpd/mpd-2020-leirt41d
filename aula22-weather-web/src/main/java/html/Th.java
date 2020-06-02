package html;

public class Th extends Element {
    private static final String NAME="th";

    public Th(Node... childs) {
        super(NAME, childs);
    }

    public Th(String text) {
        super(NAME, new Text(text));
    }

    public Th(Integer num) {
        super(NAME, new Text(num.toString()));
    }
}
