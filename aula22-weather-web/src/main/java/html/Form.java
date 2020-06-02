package html;

public class Form  extends Element {

    public Form (String name, String method, String action, Node ... childs) {
        super("form", childs);
        addAtribute("name", name);
        addAtribute("method", method);
        addAtribute("action", action);
    }
}
