import asyncio.AsyncFile;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static asyncio.FileUtils.copyFileAsync;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static utils.StreamUtils.zip;

public class AsyncIoTests {
    // auxiliary file to synchronous tests
    private static void copyFile(String fin, String fout)  {
        try(InputStream is = new FileInputStream(fin);
            OutputStream os = new FileOutputStream(fout))  {
            byte[] buffer = new byte[4096];
            int size;
            while((size = is.read(buffer)) > 0)
                os.write(buffer, 0, size);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Stream<Path> getFilesFromFolder(String rootName) {
        Path root = Path.of(rootName);

        try {
            return Files.walk(root)
                    .filter(p -> Files.isRegularFile(p));
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    private static Stream<String> getLines(Path p) {
        try {
            return Files.lines(p);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // Synchronous tests

    private static final int UTHREAD_LINES = 526;
    private static final String UTHREAD_FILE = "uthread.c";

    @Test
    public void countLinesSequentialTest() {
        int expectedLines = UTHREAD_LINES;
        long count = getLines(Path.of(UTHREAD_FILE))
                .peek(l -> {
                    System.out.println(l); } )
                .count();
        assertEquals(526, count);
        System.out.println("lines: " + count);
    }

    @Test
    public void copy2FilesSequentialTest()  {
        String[] fin = { "fin1.dat", "fin2.dat"};
        String[] fout = {"fout1.dat", "fout2.dat"};

        copyFile("fin1.dat","fout1.dat" );
        copyFile("fin2.dat","fout2.dat" );
    }

    @Test
    public void countFilesLinesSequentialTest()  {
        long start = System.currentTimeMillis();
        Stream<Path> files = getFilesFromFolder("rdf-files.tar");

        long result = files
                .flatMap(AsyncIoTests::getLines)
                .count();
        System.out.println(result
                + ", done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }


    // End of sequential tests

    @Test
    public void countLinesWithAsyncFileTest() {

        int expectedLines = 526;
        AsyncFile file = AsyncFile.open(UTHREAD_FILE);

        // what about file closing ??

        long count =
                file.readLines()
                .join()
                .count();

        file.close();
        assertEquals(expectedLines, count);
        System.out.println("lines: " + count);
    }

    @Test
    public void CountLinesOf2TextFileTest() {
        AsyncFile file1 = AsyncFile.open(UTHREAD_FILE);
        AsyncFile file2 = AsyncFile.open(UTHREAD_FILE);

        int expectedLines = UTHREAD_LINES*2;

       // to complete

        long start = System.currentTimeMillis();

        // versão com encadeamento em série
        CompletableFuture<Long> futAllSerial =
                file1.readLines()
                .thenCompose(stream1 ->
                        file2.readLines()
                        .thenApply( stream2 -> stream1.count() + stream2.count())
                )
                .whenComplete((l, exc) -> { file1.close(); file2.close(); });
        assertEquals(expectedLines, futAllSerial.join().intValue());
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");

        // versão executando em paralelo a geração das duas Stream<String>
        AsyncFile file1a = AsyncFile.open(UTHREAD_FILE);
        AsyncFile file2a = AsyncFile.open(UTHREAD_FILE);

        start = System.currentTimeMillis();
        CompletableFuture<Long> futAllPar =
                file1a.readLines()
                .thenCombine(file2a.readLines(),
                        (stream1, stream2) -> stream1.count() + stream2.count())
                .whenComplete((l, exc) -> { file1a.close(); file2a.close(); });
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");


        assertEquals(expectedLines, futAllPar.join().intValue());
    }


    @Test
    public void ShowLinesOfTextFileTest() {
        AsyncFile file = AsyncFile.open(UTHREAD_FILE);

        CompletableFuture<Void> result =
            file.readLines().thenAccept(sl -> {
                sl.forEach(System.out::println);
                file.close();
            });

        result.join();
    }

    CompletableFuture<Void> readLinesFromFileAsync(AsyncFile file, int number) {
        return file.readLine()
        .thenCompose(l -> {
            if (l == null) return CompletableFuture.completedFuture(null);
            else {
                System.out.printf("T[%d], L[%d]: %s\n",
                        Thread.currentThread().getId(),
                        number,
                        l);
                return readLinesFromFileAsync(file, number+1);
            }
        });
    }

    @Test
    public void ShowLinesOfTextFileWithReadlineTest() {
        AsyncFile file = AsyncFile.open(UTHREAD_FILE);

        CompletableFuture<Void>  allLines = readLinesFromFileAsync(file,1);


        allLines.join();
    }



    @Test
    public void copy2FilesWithCompletableFutureTest() {
        String[] fin = { "fin1.dat", "fin2.dat"};
        String[] fout = {"fout1.dat", "fout2.dat"};
        long start = System.currentTimeMillis();

        CompletableFuture<Long> fl1 = copyFileAsync(fin[0], fout[0]);
        fl1.join();

        CompletableFuture<Long> fl2 = copyFileAsync(fin[1], fout[1]);
        CompletableFuture<Long> fres =
                fl1.thenCombine( fl2, (l1,l2) -> l1 +l2);

        long l = fres.join();
        System.out.println(l);
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

    @Test
    public void copy2FilesInSequenceWithCompletableFutureTest() {
        String[] fin = { "fin1.dat", "fin2.dat"};
        String[] fout = {"fout1.dat", "fout2.dat"};
        long start = System.currentTimeMillis();

        // to complete
        CompletableFuture<Long> fres = null;

        long l = fres.join();
        System.out.println(l);
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }



    @Test
    public void copyNFilesWithCompletableFutureAllOfTest() {
        String[] fin = { "fin1.dat", "fin2.dat", "fin3.dat", "fin4.dat" };
        String[] fout = { "fout.dat", "fout2.dat", "fout3.dat", "fout4.dat" };

        long start = System.currentTimeMillis();
        List<CompletableFuture<Long>> futureCopies =
                IntStream.range(0, fin.length)
                        .boxed()
                        .map(i -> copyFileAsync(fin[i], fout[i]))
                        .collect(toList());

        CompletableFuture<Long>[] futCounts =
                futureCopies.toArray(s-> new CompletableFuture[s]);


        CompletableFuture<?> all =  CompletableFuture.allOf(futCounts);

        // A implementar
        CompletableFuture<Long> total = null;



        System.out.println(total.join());
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

    @Test
    public void copyNFilesWithCompletableFutureCombineTest() {
        String[] fin = { "fin1.dat", "fin2.dat", "fin3.dat", "fin4.dat" };
        String[] fout = { "fout.dat", "fout2.dat", "fout3.dat", "fout4.dat" };

        long start = System.currentTimeMillis();
        List<CompletableFuture<Long>> futureCopies =
                IntStream.range(0, fin.length)
                        .boxed()
                        .map(i -> copyFileAsync(fin[i], fout[i]))
                        .collect(toList());

        // A implementar
        CompletableFuture<Long> total = null;

        System.out.println(total);
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

    @Test
    public void copyNFilesWithCompletableFutureAllOf2Test() {
        Stream<String> fins =
                Stream.of("fin1.dat", "fin2.dat", "fin3.dat", "fin4.dat");
        Stream<String> fouts =
                Stream.of("fout.dat", "fout2.dat", "fout3.dat", "fout4.dat");
        long start = System.currentTimeMillis();
        Stream<CompletableFuture<Long>> futureCopies =
                zip(fins, fouts,
                    (fin, fout) -> copyFileAsync(fin, fout));


        CompletableFuture<Long>[] futCounts =
                futureCopies.toArray(s-> new CompletableFuture[s]);


        CompletableFuture<Long> total =
                CompletableFuture.allOf(futCounts)
                .thenApply(__ ->
                    Arrays.stream(futCounts)
                        .mapToLong(f -> f.join())
                        .sum()
                );

        System.out.println(total.join());
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }




    @Test
    public void countMultipleFilesLinesAsyncWithAllOfTest() {

        long start = System.currentTimeMillis();
        Stream<Path> files = getFilesFromFolder("rdf-files.tar");

        Stream<CompletableFuture<Long>> futCounts =
                files.map(p -> {
                        AsyncFile f = AsyncFile.open(p.toString());
                        return f.readLines()
                                .thenApply(s -> s.count())
                                .whenComplete((l, __) -> f.close());
                });

        CompletableFuture<Long>[] futArray =
                futCounts.toArray(s-> new CompletableFuture[s]);

        System.out.println("Start count!");

        CompletableFuture<Long> result =
                CompletableFuture
                        .allOf(futArray)
                        .thenApply(__ -> {
                            return Stream.of(futArray)
                                    .mapToLong(f -> f.join())
                                    .sum();
                        });

        System.out.println(result.join()
                + ", done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

    @Test
    public void countMultipleFilesLinesAsyncWithCombineTest() {
        long start = System.currentTimeMillis();
        Stream<Path> files = getFilesFromFolder("rdf-files.tar");


        Stream<CompletableFuture<Long>> futCounts =
                files.map(p -> {
                    AsyncFile f = AsyncFile.open(p.toString());
                    return f.readLines()
                            .whenComplete((t, s) -> f.close())
                            .thenApply(s -> s.count());
                });

        System.out.println("Start count!");

        CompletableFuture<Long> result = futCounts
                .reduce((f1,f2) -> {
                    return f1.thenCombine(f2, (l1,l2)-> l1+l2 );
                })
                .orElse(CompletableFuture.completedFuture(0L));

        System.out.println(result.join()
                + ", done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

    private CompletableFuture<Long>
    countLinesFromAsyncFile(AsyncFile af, long total) {
        return af.readLine()
            .thenCompose(l -> {
                if (l == null) return CompletableFuture.completedFuture(total);
                else return countLinesFromAsyncFile(af, total+1);
            });
    }

    private CompletableFuture<Long> countLinesAsyncFile(AsyncFile af) {
        return countLinesFromAsyncFile(af,0);
    }

    @Test
    public void countLinesOfTextFileWithReadlineTest() {
        int expectedLines = 526;
        AsyncFile file = AsyncFile.open(UTHREAD_FILE);

        CompletableFuture<Long>
                allLines = countLinesAsyncFile(file );
        long count = allLines.join();

        file.close();

        assertEquals(expectedLines, count);
        System.out.println("lines: " + count);
    }

    @Test
    public void countMultipleFilesLinesWithAsyncFileReadLineTest() {
        long start = System.currentTimeMillis();
        Stream<Path> files = getFilesFromFolder("rdf-files.tar");

        System.out.println("Start count!");


        Stream<CompletableFuture<Long>> futCounts =
                files.map(p -> {
                    AsyncFile f = AsyncFile.open(p.toString());
                    return  countLinesAsyncFile(f)
                            .whenComplete((t, s) -> f.close());

                });


        CompletableFuture<Long> result = futCounts
                .reduce((f1,f2) -> {
                    return f1.thenCombine(f2, (l1,l2)-> l1+l2 );
                })
                .orElse(CompletableFuture.completedFuture(0L));

        System.out.println(result.join()
                + ", done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

    AtomicInteger total =new AtomicInteger(0);

    int getOccurences(String line, String word) {
        //System.out.println("'" + line + "'" + ": " + word);
        long res= Arrays.stream(line.split(" "))
        .filter( w -> w.contains(word))
        .count();

        total.addAndGet((int)res);
        return (int) res;
    }

    @Test
    public void countWordOccurrencesInMultipleFilesWithCombineTest() {
        long start = System.currentTimeMillis();
        Stream<Path> files = getFilesFromFolder("rdf-files.tar");
        String word = "file";
        total.set(0);

        Stream<CompletableFuture<Long>> futCounts =
                files.map(p -> {
                    AsyncFile f = AsyncFile.open(p.toString());
                    return f.readLines()
                            .thenApply( s -> s.map(str -> getOccurences(str, word)))
                            .thenApply(s -> s.mapToLong(i -> i).sum())
                            .whenComplete((t, s) -> f.close());
                });

        System.out.println("Start count!");

        CompletableFuture<Long> result = futCounts
                .reduce((f1,f2) -> {
                    return f1.thenCombine(f2, (l1,l2)-> l1+l2 );
                })
                .orElse(CompletableFuture.completedFuture(0L));
        System.out.println(total.get());
        System.out.println(result.join()
                + ", done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

    private CompletableFuture<Long>
    countOccursFromAsyncFile(AsyncFile af, String word, long total) {
        return af.readLine()
                .thenCompose(l -> {
                    if (l == null) return CompletableFuture.completedFuture(total);
                    //System.out.println(l);
                    return countOccursFromAsyncFile(af, word, total+ getOccurences(l, word));
                });
    }

    private CompletableFuture<Long> countOccursAsyncFile(AsyncFile af, String word) {
        return countOccursFromAsyncFile(af, word,0);
    }

    @Test
    public void countWordOccurrencesInMultipleFilesWithReadLineTest() {
        long start = System.currentTimeMillis();
        Stream<Path> files = getFilesFromFolder("rdf-files.tar");
        String word = "file";

        total.set(0);
        Stream<CompletableFuture<Long>> futCounts =
                files.map(p -> {
                    AsyncFile f = AsyncFile.open(p.toString());
                    return  countOccursAsyncFile(f, word)
                            .whenComplete((t, s) -> f.close());
                });

        System.out.println("Start count!");

        CompletableFuture<Long> result = futCounts
                .reduce((f1,f2) -> {
                    return f1.thenCombine(f2, (l1,l2)-> l1+l2 );
                })
                .orElse(CompletableFuture.completedFuture(0L));
        System.out.println("total = " + total.get());
        System.out.println(result.join()
                + ", done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }


}
