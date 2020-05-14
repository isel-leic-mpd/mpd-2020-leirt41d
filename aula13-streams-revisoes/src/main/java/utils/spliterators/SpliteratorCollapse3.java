package utils.spliterators;

import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

public class SpliteratorCollapse3<T> extends Spliterators.AbstractSpliterator<T>{

    private Spliterator<T> src;
    private Optional<T> box = Optional.empty();

    public SpliteratorCollapse3(Spliterator<T> src){
        super(Long.MAX_VALUE, ORDERED );
        this.src = src;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) {
        boolean[] oneMore={false};


        while(src.tryAdvance(elem->{

            if(!box.isPresent()){
                box = box.of(elem);
                action.accept(box.get());
            }
            else if(elem != box.get()){
                box = box.of(elem);
                action.accept(elem);
                oneMore[0] = true;

            }
        }));
        return oneMore[0];
    }
}