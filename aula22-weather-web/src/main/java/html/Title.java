package html;

public class Title  extends Element {
    private static final String NAME="title";

    public Title(String text) {
        super(NAME, new Text(text));
    }
}
