package utils;

import java.util.NoSuchElementException;

public class Box<T> {
    private final boolean isPresent;
    private final T item;

    private Box(T item) {
        this.isPresent = true;
        this.item = item;
    }

    private Box() {
        this.isPresent = false;
        this.item = null;
    }

    public static <T> Box<T> of(T item) {
        return new Box<>(item);
    }
    public static <T> Box<T> empty() {
        return new Box<>();
    }

    public boolean isPresent() {
        return isPresent;
    }

    public T get() {
        if (!isPresent()) throw new NoSuchElementException();
        return item;
    }
}