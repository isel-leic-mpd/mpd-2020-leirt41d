
import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import static java.lang.Math.sqrt;
import static utils.StreamUtils.*;


public class StreamTestsAula11 {

    @Test
    public void zipSimpleTest() {
        Stream<Integer> numbers = Stream.of(1, 2, 3);

        Stream<String> names = Stream.of("a" , "b", "c", "d");

        Stream<String> zipped =
                zip(names, numbers, (s, i) -> s + i);

        List<String> expected = List.of("a1", "b2", "c3");

        assertEquals(expected, zipped.collect(toList()));
    }


    @Test
    public void showFileNumbersTest() {
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" + currentDir);

        String fileName =
                currentDir + "/src/main/java/utils/StreamUtils.java";

        Stream<String> numberedLines = null; // TO DO


        numberedLines.forEach(l -> System.out.println(l));
    }

    @Test
    public void generateRange1to100PairCombinationsTest() {
       Stream<Pair<Integer,Integer>>
            combs = IntStream.rangeClosed(1,100)
                .boxed()
                .flatMap(i -> {
                    Stream<Pair<Integer,Integer>> pairs =
                         IntStream.rangeClosed(1,100)
                        .mapToObj(j -> new Pair<>(i,j));
                    return pairs;
                });
       combs.forEach(System.out::println);
    }

    @Test
    public void generateCouplesTest() {
        Stream<String> femaleNames =
                Stream.of("Ana", "Maria", "Catarina", "Rute");

        List<String> maleNames =
                List.of("Joao", "Diogo", "Carlos", "Luis");

        Stream<Pair<String,String>> couples =
                femaleNames
                .flatMap(n1 ->
                        maleNames.stream()
                        .map(n2 -> new Pair<>(n1,n2))
                );

        couples.forEach(System.out::println);
    }

    @Test
    public void collapseTest() {
        Stream<Integer> collapsed =
                collapse(
                        IntStream.of(2, 2, 1, 3, 4, 4, 4, 1, 1, 2, 2, 2)
                                .boxed());
        List<Integer> expected = List.of(2,1,3,4,1,2);

        assertEquals(expected, collapsed.collect(toList()));
    }


    /*
    @Test
    public void cacheRandomSeqTest() {
        Random r = new Random();
        Stream<Integer> numbers =
                Stream.generate(() -> r.nextInt(100));
        Supplier<Stream<Integer>> nrs = cache(numbers);
        Object[] expected = nrs.get().limit(10).toArray();
        Object[] actual = nrs.get().limit(10).toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void cacheRandomSeqWithExternalIterationTest() {
        Random r = new Random();
        Stream<Integer> numbers =
                Stream.generate(() -> r.nextInt(100));
        Supplier<Stream<Integer>> nrs = cache(numbers);
        Spliterator<Integer> it1 = nrs.get().limit(10).spliterator();
        Spliterator<Integer> it2 = nrs.get().limit(10).spliterator();

        List<Integer> random1 = new ArrayList<>();
        List<Integer> random2 = new ArrayList<>();

        // read first two from it1
        it1.tryAdvance(n -> random1.add(n));
        it1.tryAdvance(n -> random1.add(n));

        // read first three from it2
        it2.tryAdvance(n -> random2.add(n));
        it2.tryAdvance(n -> random2.add(n));
        it2.tryAdvance(n -> random2.add(n));

        // read remaining from it1
        it1.forEachRemaining(n -> random1.add(n));

        // read remaining from it2
        it2.forEachRemaining(n -> random2.add(n));

        assertEquals(random1, random2);
    }

    */

}
