// Simo Münc

package development.main;

import development.data.DataFile;
import development.data.Database;
import engine.tools.Game;
import engine.tools.Settings;
import engine.tools.WaveAudio;

public class Program {

    public static final WaveAudio music = new WaveAudio("audio\\music.wav");
    public static Game game;

    public static void main(String[] args) {

        DataFile.load();
        Database.load();

        music.play(true);
        music.setVolume(DataFile.getMusicVolume());

        Settings settings = new Settings(10, 6);
        Environment environment = new Environment();

        // Starten des Spiels
        game = new Game(settings, environment, "img\\ui\\icon.png");
        game.run();
    }
}