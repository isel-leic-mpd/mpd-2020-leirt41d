package futures;

import utils.Pair;

import java.util.concurrent.CompletableFuture;

public class MoreCompletableFutureExamples extends FutureExamples {

    public static String getStatsInfo(Pair<Long, Long> results) {
        return results.toString();
    }

    public static CompletableFuture<String>
    anotherAsyncOper(Pair<Long, Long> results) {
       return CompletableFuture.supplyAsync( () -> results.toString());
    }

}
