package travel.agencies.utils;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.ToDoubleFunction;


public class ListUtils {

    /**
     * @param elems must not be empty, throws InvalidParameterException if not
     * @param cmp   the comparator for establishing the order relation
     * @return the smaller element
     */
    public static <T>
    T smaller(Iterable<T> elems, Comparator<T> cmp) {
        T min = null;

        // A COMPLETAR!
        return min;
    }

    /**
     * @param elems must not be empty, throws InvalidParameterException if not
     * @param cmp   the comparator for establishing the order relation
     * @return the largest element
     */
    public static <T>
    T larger(Iterable<T> elems, Comparator<T> cmp) {
        // A COMPLETAR!
        return null;
    }

    /**
     *
     * @param elems
     * @param val
     * @return
     */
    public static <T> int  sum(Iterable<T> elems, ToDoubleFunction<T> val) {
        int s = 0;

       // A COMPLETAR!
        return s;
    }
}