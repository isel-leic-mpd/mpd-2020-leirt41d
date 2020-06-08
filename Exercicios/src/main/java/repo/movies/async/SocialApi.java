package repo.movies.async;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class SocialApi {
    // asynchronously  return the favourit movies sequence for
    // the user with id "userId" .
    public static CompletableFuture<Stream<String>>
    getFavouritesMoviesTitlesAsync(int userId){ return null; }
}
