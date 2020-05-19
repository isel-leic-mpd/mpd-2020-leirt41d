import asyncio.AsyncFile;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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

        /*
        // versão com encadeamento em série
        CompletableFuture<Long> futAllSerial =
                file1.readLines()
                .thenCompose(stream1 ->
                        file2.readLines()
                        .thenApply( stream2 -> stream1.count() + stream2.count()));
        */

        // versão executando em paralelo a geração das duas Stream<String>
        CompletableFuture<Long> futAllPar =
                file1.readLines()
                .thenCombine(file2.readLines(),
                        (stream1, stream2) -> stream1.count() + stream2.count())
                .whenComplete((l, exc) -> { file1.close(); file2.close(); });


        assertEquals(expectedLines, futAllPar.join().intValue());
    }


    @Test
    public void ShowLinesOfTextFileTest() {
        AsyncFile file = AsyncFile.open(UTHREAD_FILE);
        CompletableFuture<Stream<String>> lines =
                file.readLines();

        CompletableFuture<Void> result = lines.thenAccept(sl -> {
            sl.forEach(System.out::println);
            file.close();
        });

        result.join();
    }



    @Test
    public void copy2FilesWithCompletableFutureTest() {
        String[] fin = { "fin1.dat", "fin2.dat"};
        String[] fout = {"fout1.dat", "fout2.dat"};
        long start = System.currentTimeMillis();

        CompletableFuture<Long> fl1 = copyFileAsync(fin[0], fout[0]);

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

}
