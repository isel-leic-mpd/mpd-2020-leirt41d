import org.junit.Test;
import utils.Pair;

import static model.SyncOpers.results;
import static org.junit.Assert.assertEquals;
import static threads.ThreadExamples.helloThread;
import static threads.ThreadExamples.resultsPar;

public class ThreadTests {
    @Test
    public void threadHelloTest() throws InterruptedException {
        helloThread();
    }

    @Test
    public void resultsTest() {
        Pair<Long,Long> p;
        int vals[] = {1,2,3,4};
        long start = System.currentTimeMillis();

        p = results(vals);

        long expectedSum = 10;
        long expectedMul = 24;

        System.out.printf("resultsTest done in %dms\n",
                System.currentTimeMillis()-start);
        assertEquals(expectedSum, (long) p.first);
        assertEquals(expectedMul, (long) p.sec);
    }

    @Test
    public void resultsParTest() {
        Pair<Long,Long> p;
        int vals[] = {1,2,3,4};
        long start = System.currentTimeMillis();

        p = resultsPar(vals);

        long expectedSum = 10;
        long expectedMul = 24;

        System.out.printf("resultsTest done in %dms\n",
                System.currentTimeMillis()-start);
        assertEquals(expectedSum, (long) p.first);
        assertEquals(expectedMul, (long) p.sec);
    }
}
