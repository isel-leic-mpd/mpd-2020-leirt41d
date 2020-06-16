import org.junit.Test;
import repo.functions.MyBiFunction;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CurryTests {
    @Test
    public void testCurry() {
        MyBiFunction<String, String, Integer> sumLens = (s1, s2) -> s1.length() + s2.length();
        String str1 = "Portugal"; String str2 = "Campeão";

        assertEquals(sumLens.apply(str1, str2), sumLens.curry().apply(str1).apply(str2));
    }


    @Test public void testCurryWithThreeArguments() {
        /* to complete ____ */ sumLens =  /* to complete ____ */

        String str1 = "Portugal"; String str2 = "Campeão"; String str3 = "2018/2019";
        assertTrue(str1.length() + str2.length() + str3.length() ==
                sumLens.apply(str1).apply(str2).apply(str3));
    }
}
