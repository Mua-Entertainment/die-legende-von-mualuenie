// Simo Münc

package engine.tools;

import engine.main.GameObject;
import engine.main.Window;

// Klasse mit Grundeinstellungen und Umgebung
public record Game(Settings settings, GameObject environment, String iconSrc) {

    // öffnet das Fenster
    public void run() {
        new Window(settings, environment, iconSrc);
    }
}