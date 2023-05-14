package engine;

import java.util.function.Consumer;

public class Event<T> {

    private final SafeList<Consumer<T>> subscribers = new SafeList<>();

    public void subscribe(Consumer<T> subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Consumer<T> subscriber) {
        subscribers.remove(subscriber);
    }

    public void invoke(T value) {
        subscribers.forEach(sub -> {
            sub.accept(value);
        });
    }
}