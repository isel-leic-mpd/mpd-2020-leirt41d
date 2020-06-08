package repo.movies.reactive;

import io.reactivex.Observable;

import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import static repo.movies.reactive.MoviesApi.getRating;
import static repo.movies.reactive.SocialApi.getFavouritesMoviesTitles;

public class Answers {

    // a)
    // use the SocialApi and MovieApi classes of this package
    Observable<Movie>
    getFavouritesMovies(int userId)  {
        // to complete
        return null;
    }


    // b)
    // For this option use the SocialApi and MovieApi classes of
    // repo.movies.async package!
    Observable<Movie>
    getFavouritesMovies2(int userId)  {
        // to complete
        return null;
    }


}
