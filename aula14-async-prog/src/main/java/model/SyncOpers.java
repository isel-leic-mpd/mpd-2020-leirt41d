package model;

import utils.Pair;

import static utils.ThreadUtils.sleep;

public class SyncOpers {

    public  static Long sum(int[] vals) {
        sleep(2000);
        long total = 0;
        for(int v : vals) total += v;
        return total;
    }

    public static long mul(int[] vals) {
        sleep(4000);

        long total = 1;
        for(int v : vals) {
            if (v != 0)
                total *= v;
        }
        return total;
    }

    public static Pair<Long,Long> results(int[] vals) {
        return new Pair<>(sum(vals), mul(vals));
    }

}
