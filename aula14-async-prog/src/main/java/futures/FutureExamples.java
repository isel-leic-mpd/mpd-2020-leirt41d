package futures;

import utils.Pair;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static model.SyncOpers.*;
import static utils.ThreadUtils.sleep;

public class FutureExamples {
    private static final ExecutorService pool =
            Executors.newFixedThreadPool(
                    Runtime.getRuntime().availableProcessors());

    public static Future helloFuture() {
        Future fut =

                pool.submit(() -> {
                    sleep(2000);
                    System.out.println("Hello from future in thread "
                            + Thread.currentThread().getId());
                });
        return fut;
    }


    private static Future<Long> sumAsync(int[] vals) {
        return pool.submit( () -> sum(vals));
    }

    private static Future<Long> mulAsync(int[] vals) {
        return pool.submit( () -> mul(vals));
    }

    public static Future<Pair<Long,Long>> resultsAsync(int[] vals) {
        Future<Long> f1 = sumAsync(vals);
        Future<Long> f2 = mulAsync(vals);

        return pool.submit(() -> new Pair<>(f1.get(), f2.get()));
    }

    private static void sumAsyncCb(int[] vals,
                                   Consumer<Long> action) {
        pool.submit( () -> {
            action.accept(sum(vals));
        });
    }

    private static void mulAsyncCb(int[] vals,
                                   Consumer<Long> action) {
        pool.submit( () -> {
            action.accept(mul(vals));
        });
    }

    public static void resultsAsyncCb(int[] vals,
                                      Consumer<Pair<Long,Long>> action) {
        AtomicInteger ncalls = new AtomicInteger(0);

        long[] res = {0,0};

        sumAsyncCb(vals,
                l -> {
                    res[0] = l;
                    if (ncalls.incrementAndGet() == 2)
                        action.accept(new Pair<>(res[0],res[1]));

                });
        mulAsyncCb(vals,
                l -> {
                    res[1] = l;
                    if (ncalls.incrementAndGet() == 2)
                        action.accept(new Pair<>(res[0],res[1]));
                });
    }


    private static CompletableFuture<Long> sumAsyncCf(int[] vals) {
        CompletableFuture<Long> cf  = new CompletableFuture<>();
        pool.submit(() -> cf.complete(sum(vals)));
        return cf;
    }

    private static CompletableFuture<Long> mulAsyncCf(int[] vals) {
        CompletableFuture<Long> cf  = new CompletableFuture<>();
        pool.submit(() -> cf.complete(mul(vals)));
        return cf;
    }

    public static CompletableFuture<Pair<Long,Long>>
    resultsAsyncCf(int[] vals) {
        /*
         CompletableFuture<Long> cfs = sumAsyncCf(vals);
         CompletableFuture<Long> cfm = mulAsyncCf(vals);

         CompletableFuture<Pair<Long,Long>> cf_all =
                cfs.thenCombine(cfm, (l1,l2) -> new Pair<>(l1,l2));
         return cf_all;

         */
        return sumAsyncCf(vals)
                .thenCombine(mulAsyncCf(vals), (l1,l2) -> new Pair<>(l1,l2));
    }
}
