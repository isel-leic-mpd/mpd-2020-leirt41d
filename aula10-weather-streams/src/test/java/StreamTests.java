
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Optional;
import java.util.OptionalLong;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import static java.lang.Math.sqrt;


public class StreamTests {

    private static boolean isPrime(long n) {
        if (n ==2) return true;
        if (n % 2 ==0 || n < 2) return false;
        for(long t = 3; t < sqrt(n); t += 2)
            if (n % t == 0) return false;
        return true;
    }

    @Test
    public void getFirstMillionPrimesSequentialTest() {
        long start = System.currentTimeMillis();

        Stream<Long> candidates =
            Stream.concat(
                Stream.of( (long) 2),
                Stream.iterate((long) 3, n -> n +2));

        Stream<Long> primes =
                candidates.filter(StreamTests::isPrime)

                        .limit(1000000);

        Optional<Long> lastMillionPrime =
               primes.reduce((l1, l2) -> l2);


        assertTrue(lastMillionPrime.isPresent());

        assertEquals(15476729, (long) lastMillionPrime.get());
        System.out.println("last million prime is: " + lastMillionPrime);
        long end = System.currentTimeMillis();
        System.out.println("done in " + (end-start) + "ms");
    }

    @Test
    public void getFirstMillionPrimesSequentialWithLongStreamTest() {
        long start = System.currentTimeMillis();

        LongStream candidates =
                LongStream.concat(
                        LongStream.of( 2),
                        LongStream.iterate( 3, n -> n +2));

        LongStream primes =
                candidates.filter(StreamTests::isPrime)
                        .limit(1000000);


        OptionalLong lastMillionPrime =
                primes.reduce((l1, l2) -> l2);

        assertTrue(lastMillionPrime.isPresent());

        assertEquals(15476729, lastMillionPrime.getAsLong());
        System.out.println("last million prime is: " + lastMillionPrime);
        long end = System.currentTimeMillis();
        System.out.println("done in " + (end-start) + "ms");
    }


    @Test
    public void getFirstMillionPrimesParallelTest() {
        long start = System.currentTimeMillis();

        Stream<Long> candidates =
                Stream.concat(
                        Stream.of( (long) 2),
                        Stream.iterate((long) 3, n -> n +2));

        Stream<Long> primes =
                candidates.filter(StreamTests::isPrime)

                        .limit(1000000);

        Optional<Long> lastMillionPrime =
                primes
                .parallel()
                .reduce((l1, l2) -> l2);


        assertTrue(lastMillionPrime.isPresent());

        assertEquals(15476729, (long) lastMillionPrime.get());
        System.out.println("last million prime is: " + lastMillionPrime);
        long end = System.currentTimeMillis();
        System.out.println("done in " + (end-start) + "ms");
    }

    @Test
    public void getFirstMillionPrimesParallelWithLongStreamTest() {
        long start = System.currentTimeMillis();

        LongStream candidates =
                LongStream.concat(
                        LongStream.of( 2),
                        LongStream.iterate( 3, n -> n +2));

        LongStream primes =
                candidates
                        .parallel()
                        .filter(StreamTests::isPrime)
                        .limit(1000000);


        OptionalLong lastMillionPrime =
                primes.reduce((l1, l2) -> l2);


        assertTrue(lastMillionPrime.isPresent());

        assertEquals(15476729, lastMillionPrime.getAsLong());
        System.out.println("last million prime is: " + lastMillionPrime);
        long end = System.currentTimeMillis();
        System.out.println("done in " + (end-start) + "ms");
    }

}
