package repo.music;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static repo.movies.async.SocialApi.getFavouritesMoviesTitlesAsync;
import static repo.music.MusicApi.getMusicsTitlesAsync;
import static repo.music.MusicApi.getPlaycountAsync;
import static repo.music.SocialApi.getFavSingerAsync;

public class Answers {
    // CompletableFuture<List<Music>> getMusicsAsync(String mbid)
    // retorna uma lista de músicas do cantor identificado por mbid, na forma de CompletableFuture<List<Music>>.
    // Cada música é uma instância de uma nova classe Music com o título da música e o número de vezes que já foi tocada
    // (escreva também a sua implementação da classe Music)

    CompletableFuture<List<Music>> getMusicsAsync(String mbid) {
        // to complete
        return null;
    }

    CompletableFuture<List<Music>> getFavMusicsAsync(int userId) {
         // to complete
        return null;
    }

    CompletableFuture<List<Music>> getFavMusicsAsync(IntStream ids) {
        // to complete
        return null;
    }
}
