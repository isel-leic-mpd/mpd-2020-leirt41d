package meals;

import java.util.Optional;

/**
 * A classe Meal representa uma refeição constituída por dois pratos
 * ou opcionalmente por um terceiro prato
 */
public class Meal {
    private final Dish dish1, dish2;
    private Optional<Dish> dish3;

    public Meal(Dish dish1, Dish dish2) {
        this.dish1=dish1; this.dish2=dish2;
        this.dish3=Optional.empty();
    }

    public Meal(Dish dish1, Dish dish2, Dish dish3) {
        this.dish1 = dish1; this.dish2 = dish2;
        this.dish3 = Optional.of(dish3);
    }

    public Meal(Dish dish1, Dish dish2,
                Optional<Meal> meal) {
        this.dish1 = dish1; this.dish2 = dish2;
        this.dish3 = meal.filter(m ->
                        m.dish1.getType() == Dish.Type.VEGETAL &&
                        m.dish2.getType() == Dish.Type.VEGETAL
                    )
                    .flatMap(m -> m.dish3);
    }

    @Override
    public String toString() {
        return String.format("{ %s and %s %s}",
                dish1.getName(),
                dish2.getName(),
                dish3.map(d -> "and " + d.getName()).orElse(""));
    }

    @Override public boolean equals(Object o) {
        if (o.getClass() != Meal.class) return false;
        Meal other = (Meal) o;
        return dish1.equals(other.dish1) &&
                dish2.equals(other.dish2) &&
                dish3.equals(other.dish3);
    }

    public int getCalories() {
        int dish3Calories;

        if (dish3.isPresent())
            dish3Calories = dish3.get().getCalories();
        else
            dish3Calories = 0;

        //Optional<Integer> t = dish3.map(d -> d.getCalories());
        return dish1.getCalories() +
                dish2.getCalories() +
                dish3.map(d -> d.getCalories()).orElse(0);

    }

    public Dish dish1() { return dish1; }

    public Dish dish2() { return dish2; }

    public Optional<Dish> dish3() { return dish3; }
}