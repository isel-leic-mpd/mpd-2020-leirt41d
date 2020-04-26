package meals;

/**
 * A classe Dish representa as característica de um prato
 * de carne, peixe, vegetariano ou sobremesa
 */
public class Dish {
    public enum Type { MEAT, FISH, VEGETAL, DESERT }

    private final String name;
    private final int calories;
    private final Type type;
    private final double price;
    public Dish(String name,  int calories, Type type, double price) {
        this.name = name;
        this.calories = calories;
        this.type = type;
        this.price = price;
    }

    public String getName() { return name; }
    public int getCalories() { return calories; }
    public Type getType() { return type; }
    public double getPrice() { return price; }

    @Override public String toString() {
        return String.format( "{ name=%s, type=%s, calories=%d, price=%d euros }",
                name,
                type,
                getCalories(),
                (int) getPrice() );
    }
}
