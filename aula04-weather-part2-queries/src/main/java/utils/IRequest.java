package utils;

/**
 * An interface to abstract the method used for getting the contents of a resource path
 */
public interface IRequest {
    Iterable<String> getContent(String path);
}
