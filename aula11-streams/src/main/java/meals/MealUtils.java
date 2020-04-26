package meals;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

public  class MealUtils {


    // retorna a refeição menos calórica constituída por um prato com vegetais
    // e outro prato de peixe a partir da lista de pratos recebida por parâmetro
    public static Optional<Meal> LeastCaloricVegetalAndFishMeal(List<Dish> menu) {
       // A completar
       return Optional.empty();
    }

    // retorna a sequência de refeições que se podem compor a partir da lista de pratos
    // recebida por parâmetro com um prato de peixe e outro de carne.
    public static Stream<Meal> fishAndMeatMeals(List<Dish> menu) {
        // A completar
        return null;
    }

    // retorna a sequência de refeições que se podem compor a partir da lista de pratos
    // recebida por parâmetro com um prato de peixe, outro de carne e um terceiro
    // prato de sobremesa (tipo Dish.DESERT)
    public static Stream<Meal> FishAndMeatAndOtherMeals(List<Dish> menu) {
        // A completar
        return null;
    }
}