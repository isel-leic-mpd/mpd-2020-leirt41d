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
        Observable<String> titles =  getFavouritesMoviesTitles(userId).cache();
        Observable<Double> ratings = titles.flatMap(t -> getRating(t));

        return titles.zipWith(ratings, (t,r)-> new Movie(t,r));

    }

    // a)
    // use the SocialApi and MovieApi classes of this package
    Observable<Movie>
    getFavouritesMovies3(int userId)  {
        // to complete
        Observable<String> titles =  getFavouritesMoviesTitles(userId).cache();
        return titles.flatMap(t -> getRating(t)
                                    .map(r-> new Movie(t,r)));


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
