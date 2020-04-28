import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Comparator;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import meals.Dish;
import meals.Meal;
import meals.MealUtils;

public class MenuTests {

    static final List<Dish> menu =
           List.of(
               new Dish("pork", 800, Dish.Type.MEAT, 10),
               new Dish("beef",  700, Dish.Type.MEAT, 12),
               new Dish("chicken",  400, Dish.Type.MEAT, 8),
               new Dish("potatoes", 300, Dish.Type.VEGETAL, 3),
               new Dish("rice",  250, Dish.Type.VEGETAL, 2),
               new Dish("season fruit", 200, Dish.Type.DESERT, 3),
               new Dish("cake",  550, Dish.Type.DESERT, 5),
               new Dish("prawns",  300, Dish.Type.FISH, 15),
               new Dish("salmon",  450, Dish.Type.FISH, 12)
           );

    @Test
    public void menuToStreamAndBacktest() {
        Stream<Dish> menuStream = menu.stream();

        List<Dish> menu2 =
                menuStream.collect(toList());

        assertEquals(menu, menu2);

    }

    @Test
    public void menuSortedByTypeAndPriceTest() {
        Comparator<Dish> cmp =
                comparing(Dish::getType)
                .thenComparing(Dish::getPrice);

        Stream<Dish> menuSortedByTypeAndPrice =
                menu.stream()
                .sorted(cmp);

        menuSortedByTypeAndPrice.forEach(System.out::println);
    }

    @Test
    public void dishesByTypeTest() {
        Map<Dish.Type, List<Dish>> dishesByType =
                menu.stream()
                .collect(groupingBy(Dish::getType));

        for(Dish.Type t : dishesByType.keySet()) {

            System.out.println(t);
            for(Dish d: dishesByType.get(t)) {
                System.out.println(d);
            }
        }
    }


    @Test
    public void caloriesByType() {
        Map<Dish.Type, Integer> caloriesByType =
                menu.stream()
                    .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        System.out.println(caloriesByType);

    }


}
