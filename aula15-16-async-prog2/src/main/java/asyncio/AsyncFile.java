
package asyncio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.InvalidParameterException;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

/**
 * Class supporting open a file
 * for asynchronous sequential read or write
 * (binary and text)
 */
public class AsyncFile {
    // tells if file is open for read or open for write
    private enum Mode { Read, Write}

    // the transfer buffer size
    private static final int CHUNKSIZE = 4096*16;

    // the nio associated file channel
    private AsynchronousFileChannel channel;

    // read or write
    private Mode mode;

    // current read or write position in file
    private long position;

    // auxiliary function to avoid treat close exceptions
    public static void closeChannel(Channel c) {
        try { c.close(); } catch(Exception e) {}
    }

    // file factory for read mode
    public static AsyncFile open(String path) {
        Path pathIn = Paths.get(path);
        try {
            return new AsyncFile(pathIn, Mode.Read);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // file factory for write (creation) mode
    public static AsyncFile create(String path) {
        Path pathIn = Paths.get(path);
        try {
            return new AsyncFile(pathIn, Mode.Write);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // private constructor just for internal use
    private  AsyncFile(Path path, Mode mode) throws IOException {
        if (mode == Mode.Read )
            channel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
        else
            channel = AsynchronousFileChannel.open(path,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING
            );

    }

    //
    // Callback based operations
    //

    // asynchronous read chunk operation, callback based
    public void readBytes(byte[] data, int ofs, int size,
                          BiConsumer<Throwable, Integer> completed) {
        if (completed == null)
            throw new InvalidParameterException("callback can't be null!");
        if (mode == Mode.Write)
            throw new IllegalStateException("File is not readable");
        if (size + ofs > data.length)
            size = data.length - ofs;
        if (size ==0) {
            completed.accept(null, 0);
            return;
        }
        int s = size;
        ByteBuffer buf = ByteBuffer.wrap(data, ofs, size);
        CompletionHandler<Integer,Object> readCompleted =
                new CompletionHandler<Integer,Object>() {

                    @Override
                    public void completed(Integer result, Object attachment) {
                        if (result>0) position += result;
                        completed.accept(null,result);
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {

                        completed.accept(exc, null);
                    }
                };
        channel.read(buf,position, null, readCompleted);
    }

    // auxiliary  asynchronous, callback based, read operation
    public  void readBytes(byte[] data, BiConsumer<Throwable, Integer> completed) {
        readBytes(data, 0, data.length, completed);
    }

    // asynchronous write chunk operation, callback based
    public void writeBytes( byte[] data, int ofs, int size,
                            BiConsumer<Throwable, Integer> completed) {
        if (completed == null)
            throw new InvalidParameterException("callback can't be null!");
        if (mode == Mode.Read)
            throw new IllegalStateException("File is not writable");
        if (ofs + size > data.length) size = data.length - ofs;

        ByteBuffer buf = ByteBuffer.wrap(data, ofs, size);
        CompletionHandler<Integer,Object> writeCompleted =
                new CompletionHandler<Integer,Object>() {

                    @Override
                    public void completed(Integer result, Object attachment) {
                        position += result;
                        completed.accept(null, result);
                    }

                    @Override
                    public void failed(Throwable exc, Object attachment) {
                        completed.accept(exc, null);
                    }
                };

        channel.write(buf,position,null,writeCompleted);
    }

    // asynchronous write chunk operation returning a CompletableFuture
    public CompletableFuture<Integer>
    writeBytes(byte[] data, int ofs, int size) {
        CompletableFuture<Integer> completed = new CompletableFuture<>();
        writeBytes(data,ofs, size,
                (t,i) -> {
                    if (t== null) completed.complete(i);
                    else completed.completeExceptionally(t);
                });
        return completed;
    }

    //
    // CompletableFuture based operations
    //

    // asynchronous write buffer operation returning a CompletableFuture
    public CompletableFuture<Integer> writeBytes(byte[] data) {
        return writeBytes(data, 0, data.length);
    }


    // asynchronous read chunk operation returning a CompletableFuture
    public CompletableFuture<Integer> readBytes(byte[] data, int ofs, int size) {
        CompletableFuture<Integer> completed = new CompletableFuture<>();

        readBytes(data, ofs, size,
                (t,i) -> {
                    if (t== null) completed.complete(i);
                    else completed.completeExceptionally(t);
                });
        return completed;
    }

    // asynchronous read buffer operation returning a CompletableFuture
    public CompletableFuture<Integer> readBytes(byte[] data) {
        return readBytes(data,0, data.length);
    }

    //
    // Stuff needed for asynchronous readline
    //

    private static final int BUFFER_SIZE= CHUNKSIZE;
    private static final int MAX_LINE_SIZE = 4096;
    private static final int LF = '\n';
    private static final int CR = '\r';

    // the transfer buffer
    private byte[] auxline = new byte[MAX_LINE_SIZE];

    // buffer for current producing line
    private byte[] buffer = new byte[1024];

    // read position in buffer
    int bufpos = BUFFER_SIZE;

    // total bytes in buffer
    int bufsize=BUFFER_SIZE;

    // current position in producing line
    int linepos=0;

    private CompletableFuture<String> produceLine() {
        CompletableFuture<String> newLine = new CompletableFuture<>();
        try {
            String line = new String(auxline, 0, linepos, "UTF-8");
            newLine.complete(line);
        }
        catch(UnsupportedEncodingException e) {
            newLine.completeExceptionally(e);
        }
        linepos=0;
        return newLine;
    }

    // asynchronous line read operation returning a CompletableFuture<String>
    public CompletableFuture<String> readLine() {
        while(bufpos < bufsize) {
            if (buffer[bufpos] == LF) {
                if (linepos > 0 && auxline[linepos-1] == CR) linepos--;
                bufpos++;
                return produceLine();
            }
            else if (linepos == MAX_LINE_SIZE -1) return produceLine();
            else auxline[linepos++] = buffer[bufpos++];
        }
        bufpos = 0;
        return readBytes(buffer).thenCompose(res -> {
            if (res <= 0) {
                bufsize=0;
                // needed for last line that doesn't end with LF
                if (linepos > 0) {
                  return produceLine();
                }
                return CompletableFuture.completedFuture(null);
            }
            bufsize=res;
            return readLine();
        });
    }

    //
    // auxiliary functions
    //

    // auxiliary function to asynchronously read file bytes to memory
    private  CompletableFuture<Integer> copyToSequence(byte[] out, int ofs) {
        return readBytes(out, ofs, CHUNKSIZE)
                .thenCompose(i -> {
                    if (i <= 0)
                        return CompletableFuture.completedFuture(ofs);
                    return copyToSequence(out, ofs+i);
                });
    }



    // auxiliary function to get file size
    private int getSize() {
        try {
            long s = channel.size();
            if (s > Integer.MAX_VALUE) throw new RuntimeException("File too big to read!");
            return (int) s;
        }
        catch(IOException e) { throw new UncheckedIOException(e); }
    }

    //
    // public composed operations
    //

    // read all bytes to memory aysnchronously
    public CompletableFuture<ByteArrayInputStream> readAll() {
        byte[] data = new byte[getSize()];
        return copyToSequence(data, 0)
                .thenApply( i ->
                        new ByteArrayInputStream(data, 0, i));
    }

    // read all lines from a text file aysnchronously
    public CompletableFuture<Stream<String>> readLines() {
        return readAll().
                thenApply(sin -> {
                    BufferedReader reader =
                            new BufferedReader(new InputStreamReader(sin));
                    return reader.lines();
                });
    }

    public void close() {
        closeChannel(channel);
    }
}