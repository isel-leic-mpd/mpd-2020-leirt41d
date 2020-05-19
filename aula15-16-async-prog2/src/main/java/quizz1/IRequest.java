package quizz1;

import java.nio.file.Path;
import java.util.function.BiConsumer;

public interface IRequest {
    int[] get(Path path);

    default IRequest chain(BiConsumer<Path, int[]> action) {
       return path -> {
           int[] res =this.get(path);
           action.accept(path, res);
           return res;
       };
    }
}