package engine;

public class VoidEvent {

    private final SafeList<Runnable> subscribers = new SafeList<>();

    public void subscribe(Runnable subscriber) {
        subscribers.add(subscriber);
    }

    public void unsubscribe(Runnable subscriber) {
        subscribers.remove(subscriber);
    }

    public void invoke() {
        subscribers.forEach(Runnable::run);
    }
}