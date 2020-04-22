package queries.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class GenericQueries {

    // a generic mapper
    public static <T,U> Iterable<U> map(Iterable<T> src,
                                        Function<T,U> mapper  ) {
        List<U> result = new ArrayList<>();

        for(T val : src) {
            result.add(mapper.apply(val));
        }
        return result;
    }

    // a generic filter
    public static <T> Iterable<T> filter(
            Iterable<T> src, Predicate<T> selector) {
        List<T> result = new ArrayList<>();

        for(T val : src) {
            if ( selector.test(val))
                result.add(val);
        }
        return result;
    }

    // count elements
    public static <T> int count(Iterable<T> src) {
        int count = 0;
        for(T val : src) {
            count++;
        }
        return count;
    }


    // sum integer sequence
    public static int sum(Iterable<Integer> src) {
        int sum =0;
        for(int val : src)
            sum += val;
        return sum;
    }

    // sum double sequence
    public static double sumDouble(Iterable<Double> src) {
        double sum =0;
        for(double val : src)
            sum += val;
        return sum;
    }


    // max of integer sequence
    public static int max(Iterable<Integer> src) {
        int maxVal = Integer.MIN_VALUE;

        for(int temp : src) {
            if (temp > maxVal)
                maxVal = temp;
        }
        return  maxVal;
    }

    // a generic reduce operation
    // can be used to do max and sum operations and any reduce(fold) operation
    // the max reduce is done in the unitary tests
    public static <T,A> A reduce(Iterable<T> src,
                                 A initial,
                                 BiFunction<A,T,A> accum
                                 //Accumulator<A,T> accum
    ) {
        A res  = initial;
        for(T val : src) {
            res = accum.apply(res, val);
        }
        return res;
    }


}