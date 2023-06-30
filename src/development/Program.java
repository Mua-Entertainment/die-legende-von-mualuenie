// Simo Münc

package development;
import engine.Game;
import engine.Settings;
import engine.WaveAudio;

public class Program {

    public static final DataFile data = new DataFile();
    public static final Database database = new Database();
    public static final WaveAudio music = new WaveAudio("audio\\music.wav");

    public static void main(String[] args) {

        if (data.getMusicEnabled()) {
            music.play(true);
        }

        Settings settings = new Settings(10, 6, 100);
        Environment environment = new Environment();

        // Starten des Spiels
        Game game = new Game(settings, environment, "img\\ui\\icon.png");
        game.run();
    }
}