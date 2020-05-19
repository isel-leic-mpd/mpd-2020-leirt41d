import org.junit.Test;
import utils.Pair;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.Semaphore;


import static futures.FutureExamples.*;
import static futures.MoreCompletableFutureExamples.anotherAsyncOper;
import static futures.MoreCompletableFutureExamples.getStatsInfo;
import static model.SyncOpers.results;
import static org.junit.Assert.assertEquals;
import static utils.ThreadUtils.sleep;

public class MoreFutureTests {

    @Test
    public void resultsAsyncCfTest() {
        int vals[] = {1,2,3,4};

        CompletableFuture<Pair<Long,Long>> futResults = resultsAsyncCf(vals);

        long expectedSum = 10;
        long expectedMul = 24;


        Pair<Long,Long> results =
                futResults.join();

        assertEquals(expectedSum, (long) results.first);
        assertEquals(expectedMul, (long) results.sec);
    }


    @Test
    public void resultsAsyncCfWithStatsTest() {
        int vals[] = {1, 2, 3, 4};

        CompletableFuture<Pair<Long,Long>>
        futResults = resultsAsyncCf(vals);

        CompletableFuture<String>  futStats =
                futResults.thenApply( p -> getStatsInfo(p));
    }

    @Test
    public void anotherCompositionTest() {
        int vals[] = {1,2,3,4};

        CompletableFuture<String> results =
                resultsAsyncCf(vals)
                .thenCompose(p -> anotherAsyncOper(p));


    }


}
