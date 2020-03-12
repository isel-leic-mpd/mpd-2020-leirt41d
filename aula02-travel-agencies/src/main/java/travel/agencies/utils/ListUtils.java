package travel.agencies.utils;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;


public class ListUtils {

    /**
     * @param elems must not be empty, throws InvalidParameterException if not
     * @param cmp   the comparator for establishing the order relation
     * @return the smaller element
     */
    public static <T>
    T smaller(Iterable<T> elems, Comparator<T> cmp) {
        T min = null;
        /*
        for( T elem : elems) {
            if (min == null || cmp.compare(elem, min) < 0)
                min = elem;
        }
         if (min == null) throw new InvalidParameterException();

        */

        /*
        Iterator<T> it = elems.iterator();

        while(it.hasNext()) {
            T elem = it.next();
            if (min == null || cmp.compare(elem, min) < 0)
                min = elem;
        }
         if (min == null) throw new InvalidParameterException();

        */

        Iterator<T> it = elems.iterator();

        if (!it.hasNext()) throw new InvalidParameterException();
        min = it.next();

        while(it.hasNext()) {
            T elem = it.next();
            if (cmp.compare(elem, min) < 0)
                min = elem;
        }


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
    public static <T> int  sum(Iterable<T> elems, ToIntFunction<T> val) {
        int s = 0;

        for(T elem : elems)
            s += val.applyAsInt(elem);
       // A COMPLETAR!
        return s;
    }


}
