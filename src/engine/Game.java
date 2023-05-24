package engine;

// Klasse mit Grundeinstellungen und Umgebung
public record Game(Settings settings, GameObject environment, String iconSrc) {

    // öffnet das Fenster
    public void run() {
        new Window(settings, environment, iconSrc);
    }
}