package repo.movies.async;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static repo.movies.async.SocialApi.getFavouritesMoviesTitlesAsync;

public class Answers {
    // a)
    // Create a solution where we just ensure that getFavouritesMoviesAsync
    // down't block. One can block only on consulting a movie rating
    CompletableFuture<Stream<Movie>>
    getFavouritesMoviesAsync(int userId)  {
        // to complete
         return null;
    }

    // alinea b
    public static Stream<Movie>
    fromCompletableFuture(CompletableFuture<Stream<Movie>> movies) {
        // to complete
        return null;
    }

    // a), option 2
    // Create an altenative for a) where the CompletableFuture
    // return ned just completes when we can assure that movies title
    // and rating can be consulted without blocking
    CompletableFuture<Stream<Movie>>
    getFavouritesMoviesAsync2(int userId)  {
        // to complete
       return null;
    }
}
