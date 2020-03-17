package travel.agencies.utils;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.ToDoubleFunction;


public class ListUtils {

    /**
     * @param elems must not be empty
     * @param cmp   the comparator for establishing the order relation
     * @return the smaller element
     */
    public static <T>
    T smaller(Iterable<T> elems, Comparator<T> cmp) {
        T min = null;

        Iterator<T> it = elems.iterator();
        if (!it.hasNext())
            throw new InvalidParameterException();
        min = it.next();

        while(it.hasNext()) {
            T elem = it.next();
            if (cmp.compare(elem, min) < 0) min = elem;
        }

        return min;
    }

    /**
     * @param elems must not be empty
     * @param cmp   the comparator for establishing the order relation
     * @return the largest element
     */
    public static <T>
    T larger(Iterable<T> elems, Comparator<T> cmp) {

        return smaller(elems, cmp.reversed());
    }

    public static <T> int  sum(Iterable<T> elems, ToDoubleFunction<T> val) {
        int s = 0;

        for(T elem : elems) s += val.applyAsDouble(elem);
        return s;
    }
}
