package utils;

import org.asynchttpclient.AsyncHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static org.asynchttpclient.Dsl.asyncHttpClient;
import static utils.AsyncRequest.getLines;

public class HttpRequest implements AsyncRequest {
    private static void ahcClose(AsyncHttpClient client) {
        try {
            client.close();
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    @Override
    public CompletableFuture<Stream<String>>
    getContent(String path) {

        AsyncHttpClient client = asyncHttpClient();

        return  client.prepareGet(path)
                .execute()
                .toCompletableFuture()
                .thenApply( r ->
                        AsyncRequest.getLines(r.getResponseBodyAsStream()))
                .whenComplete((s,e) -> ahcClose(client));

    }
}
