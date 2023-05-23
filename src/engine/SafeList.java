package engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Consumer;

public class SafeList<T> extends ArrayList<T> {

    private boolean isLooping;

    @Override
    public void forEach(Consumer<? super T> action) {
        ArrayList<Runnable> actions = new ArrayList<>();

        super.forEach(obj -> {
            actions.add(() -> {
                action.accept(obj);
            });
        });

        actions.forEach(Runnable::run);
    }
}