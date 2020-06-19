package repo.music;


import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

class MusicApi {
    // retorna um sequência de títulos de músicas do cantor com identificador mbid.
    public static CompletableFuture<Stream<String>> getMusicsTitlesAsync(String mbid) {
       return null;
    }

    // retorna o número de vezes que foi tocada a música title do cantor identificado por mbid
    public static CompletableFuture<Integer> getPlaycountAsync(String mbid, String title) {
        return null;
    }
}