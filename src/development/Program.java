// Simo Münc

package development;
import engine.Game;
import engine.Settings;
import engine.WaveAudio;

public class Program {

    public static final WaveAudio music = new WaveAudio("audio\\music.wav");
    public static Game game;

    public static void main(String[] args) {

        DataFile.update();
        Database.update();

        if (DataFile.getMusicEnabled()) {
            music.play(true);
        }

        Settings settings = new Settings(10, 6);
        Environment environment = new Environment();

        // Starten des Spiels
        game = new Game(settings, environment, "img\\ui\\icon.png");
        game.run();
    }
}