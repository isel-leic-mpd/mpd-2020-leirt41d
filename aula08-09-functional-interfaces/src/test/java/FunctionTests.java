import interfaces.MFunction;
import org.junit.Test;

import java.util.function.Function;

import static org.junit.Assert.*;

public class FunctionTests {

    @Test
    public void compositionTest() {
        MFunction<Integer, Integer> doubler = i -> 2*i;

        MFunction<Integer, Integer> inc = i -> i+1;

        MFunction<Integer,Integer> doubleAndInc =
                doubler.andThen(inc);

        MFunction<Integer,Integer> incAndDouble =
                doubler.compose(inc);


        assertEquals(8, (int) incAndDouble.apply(3));

        assertEquals(7, (int) doubleAndInc.apply(3));


    }
}
