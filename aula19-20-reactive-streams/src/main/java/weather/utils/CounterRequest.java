package weather.utils;


import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class CounterRequest implements AsyncRequest {
    private int count;
    private AsyncRequest req;

    public CounterRequest(AsyncRequest req) {
        this.req = req;
    }



    public int getCount() { return count; }

    public void resetCount() { count = 0; }

    @Override
    public CompletableFuture<Stream<String>> getContent(String path) {
        count++;
        return req.getContent(path);
    }
}
