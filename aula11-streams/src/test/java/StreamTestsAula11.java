
import org.junit.Test;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import static java.lang.Math.sqrt;
import static utils.StreamUtils.collapse;
import static utils.StreamUtils.zip;


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

        String fileName = currentDir + "/src/main/java/utils/StreamUtils.java";
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
    public void collapseTest() {
        Stream<Integer> collapsed =
                collapse(
                        IntStream.of(2, 2, 1, 3, 4, 4, 4, 1, 1, 2, 2, 2)
                                .boxed());
        List<Integer> expected = List.of(2,1,3,4,1,2);

        assertEquals(expected, collapsed.collect(toList()));
    }


}
