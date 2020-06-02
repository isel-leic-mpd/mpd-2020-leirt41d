package html;


import java.util.stream.Stream;

public class Elements {
    public static String[] names(String... names) {
        return names;
    }

    public static String toStr(int number) {
        return Integer.toString(number);
    }

    public static Td td(Node... childs) {
        return new Td(childs);
    }

    public static H2 h2(String text) {
        return new H2(text);
    }

    public static H3 h3(String text) {
        return new H3(text);
    }

    public static Td td(String text) {
        return new Td(text);
    }

    public static Tr tr(Node... childs) {
        return new Tr(childs);
    }

    public static Th th(Node... childs) {
        return new Th(childs);
    }


    public static Table table(String ... names) {
        Node[] headerTitles = Stream.of(names)
        .map(s -> th(h3(s)))
        .toArray(sz -> new Node[sz]);
        return  new Table( tr(headerTitles));
    }

    public static Anchor a(String text, String hRef) {
        return new Anchor(text, hRef);
    }

    public static Li li(Node ...childs) {
        return new Li(childs);
    }

    public static Li li(String text) {
        return new Li(text);
    }

    public static Ul ul(Element ...childs) {
        return new Ul(childs);
    }

    public static Node text(String text) {
        return new Text(text);
    }

    public static Node text(int number) {
        return text(toStr(number));
    }

    public static Node text(char c) {
        return text(Character.toString(c));
    }
}
