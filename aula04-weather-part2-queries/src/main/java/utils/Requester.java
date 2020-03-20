package utils;

/**
 * An interface to abstract the method used for getting the contents of a resource path
 */
public interface Requester {
    Iterable<String> getContent(String path);
}
