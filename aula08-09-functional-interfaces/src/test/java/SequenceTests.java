import org.junit.Test;
import queries.Sequence;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static queries.Sequence.from;

public class SequenceTests {

    @Test
    public void filterTest() {
        Sequence<Integer> numbers =
                from(List.of(2, 5, 7, 6, 4, 9));
        Sequence<Integer> evens =
                numbers.filter( n -> n % 2 == 0);
        // while(evens.tryAdvance(n -> System.out.println(n + " ")));

        List<Integer> expected = List.of(2, 6, 4);
        assertEquals(expected, evens.toList());
    }
}
