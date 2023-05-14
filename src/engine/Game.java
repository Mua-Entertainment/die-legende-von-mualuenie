package engine;

public record Game(Settings settings, GameObject environment) {
    public void run() {
        Window window = new Window(settings, environment);
    }
}