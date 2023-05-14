package engine;

import java.util.function.BiConsumer;

public class BiEvent<K, V> {

    private final SafeList<BiConsumer<K, V>> subscribers = new SafeList<>();

    public void subscribe(BiConsumer<K, V> subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(BiConsumer<K, V> subscriber) {
        subscribers.remove(subscriber);
    }

    public void invoke(K key, V value) {
        subscribers.forEach(sub -> {
            sub.accept(key, value);
        });
    }
}
