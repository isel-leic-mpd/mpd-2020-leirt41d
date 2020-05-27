package flow;

/**
 * A component that acts as both a Subscriber and Publisher.
 *
 * @param <T> the subscribed item type
 * @param <R> the published item type
 */
public interface Processor<T,R> extends Subscriber<T>, Publisher<R> {

    static final int DEFAULT_BUFFER_SIZE = 256;

    /**
     * Returns a default value for Publisher or Subscriber buffering,
     * that may be used in the absence of other constraints.
     *
     * @implNote
     * The current value returned is 256.
     *
     * @return the buffer size value
     */
    public static int defaultBufferSize() {
        return DEFAULT_BUFFER_SIZE;
    }

}