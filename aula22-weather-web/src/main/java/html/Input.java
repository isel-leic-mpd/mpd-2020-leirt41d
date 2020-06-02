package html;

public abstract class Input extends Element {

    public static Input createFromType(String name, String type) {
        switch(type.toLowerCase()) {
            case "text" : return new InputText(name);
            default: return new InputText(name);
        }
    }

    protected Input(String name, String type, String value) {
        super("input");
        addAtribute("name", name);
        addAtribute("type", type);
        if (value != null) addAtribute("value", value);
    }

    protected Input(String name, String type ) {
        this(name, type, null);

    }
}
