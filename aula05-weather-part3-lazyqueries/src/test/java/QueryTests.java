import static org.junit.Assert.*;
import static queries.generic.lazy.Queries.*;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class QueryTests {
    @Test
    public void oddsSequenceFirst5Test() {
       int[] oddsFirst5Expected = { 1, 3, 5, 7, 9 };

       int[] oddsFirst5 = new int[5];
       int pos = 0;
       for(int i : odds()) {
           oddsFirst5[pos++] = i;
           if (pos == 5) break;
       }

       assertArrayEquals(oddsFirst5Expected, oddsFirst5);
    }

    @Test
    public void mapDoubleTest() {
        List<Integer> seqVals = List.of(3, 5, 6, 2);
        List<Integer> expectedSeq = List.of(6, 10, 12, 4);
        Iterable<Integer> doubleSeq = map(seqVals, v -> 2*v);

        List<Integer> doubleList = new ArrayList<>();

        for( Integer i : doubleSeq)
            doubleList.add(i);
        assertEquals(expectedSeq, doubleList);
    }
}
