package queries.generic.lazy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Queries {

    @SafeVarargs
    public static  <T> Iterable<T> of( T... params) {
       return Arrays.asList(params);
    }

    private static class OddsIterator implements Iterator<Integer> {
        int curr = 1;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public Integer next() {
            int ret = curr;
            curr = curr+2;
            return ret;
        }
    }

    public static Iterable<Integer> odds() {
        return () -> new OddsIterator();
    }

    public static void test() {
        Iterable<String> values =
                of("String1", "String2");

        Iterable<Integer> oddsSeq = odds();

        for(Integer i : oddsSeq)
            System.out.println(i);
    }

    public static void main(String[] args) {
        test();
    }
}
