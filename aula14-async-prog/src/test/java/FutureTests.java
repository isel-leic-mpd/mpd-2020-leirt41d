import org.junit.Test;
import utils.Pair;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;


import static futures.FutureExamples.*;
import static model.SyncOpers.results;
import static org.junit.Assert.assertEquals;

public class FutureTests {
    @Test
    public void helloFutureTest() throws Exception {
        Future f = helloFuture();

        System.out.println("Test helloFuture Returned");

        Object o = f.get();

        System.out.println(
                "Test called helloFuture in thread "
                        + Thread.currentThread().getId());

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
    public void resultsAsyncTest()
            throws InterruptedException, ExecutionException {
        Pair<Long,Long> p;
        int vals[] = {1,2,3,4};
        long start = System.currentTimeMillis();

        p = resultsAsync(vals).get();

        long expectedSum = 10;
        long expectedMul = 24;

        System.out.printf("resultsAsyncTest Done in %dms\n",
                System.currentTimeMillis()-start);

        assertEquals(expectedSum, (long) p.first);
        assertEquals(expectedMul, (long) p.sec);
    }


    @Test
    public void resultsAsyncCbTest() throws InterruptedException{
        Pair[]  results = {null};

        long start = System.currentTimeMillis();
        int vals[] = {1,2,3,4};

        Semaphore done = new Semaphore(0);
        resultsAsyncCb(vals,
            p -> {
                results[0] = p;
                done.release();
        });

        done.acquire();
        System.out.printf("resultsAsyncCbTest done in %dms\n",
                System.currentTimeMillis()-start);
        long expectedSum = 10;
        long expectedMul = 24;

        assertEquals(expectedSum, (long) results[0].first);
        assertEquals(expectedMul, (long) results[0].sec);
    }

    @Test
    public void resultsAsyncCfTest() {

        long start = System.currentTimeMillis();
        int vals[] = {1,2,3,4};

        Pair<Long,Long>  results = resultsAsyncCf(vals).join();

        System.out.printf("resultsAsyncCfTest done in %dms\n",
                System.currentTimeMillis()-start);
        long expectedSum = 10;
        long expectedMul = 24;

        assertEquals(expectedSum, (long) results.first);
        assertEquals(expectedMul, (long) results.sec);
    }


}
