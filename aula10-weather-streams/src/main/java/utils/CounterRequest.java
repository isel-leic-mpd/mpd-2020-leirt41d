package utils;

import java.io.InputStream;

public class CounterRequest extends AbstractRequest {
    private int count;
    private Request req;

    public CounterRequest(Request req) {
        this.req = req;
    }

    @Override
    public InputStream getStream(String path) {
        count++;
        return req.getStream(path);
    }

    public int getCount() { return count; }

    public void resetCount() { count = 0; }
}
