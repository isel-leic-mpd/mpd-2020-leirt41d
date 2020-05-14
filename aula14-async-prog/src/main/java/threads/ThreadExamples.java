package threads;

import utils.Pair;

import static model.SyncOpers.*;

public class ThreadExamples {
    public static void helloThread() throws InterruptedException {
        Thread t = new Thread(() -> {
            System.out.println("Hello from thread "+
                    Thread.currentThread().getId());
        });

        t.start();

        t.join();
        System.out.println("new thread created by thread " +
                Thread.currentThread().getId());

    }

    public static Pair<Long,Long> resultsPar(int[] vals) {
        long[] m = {0};

        Thread mulThread = new Thread(() -> {
             m[0] = mul(vals);
        });
        mulThread.start();

        long s = sum(vals);
        try {
            mulThread.join();
        }
        catch(InterruptedException e) {

        }
        return new Pair<>(s, m[0]);

    }
}
