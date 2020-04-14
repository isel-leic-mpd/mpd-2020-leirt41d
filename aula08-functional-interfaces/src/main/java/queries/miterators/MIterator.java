package queries.miterators;

public interface MIterator<T> {
    boolean hasNext();
    T next();
}
