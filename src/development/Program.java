package development;

import engine.Game;
import engine.Settings;
import imports.OpenSimplex2S;

public class Program {
    public static void main(String[] args) {
        Settings settings = new Settings(10, 6, 100);
        Environment environment = new Environment();
        Game game = new Game(settings, environment);

        // Fabi stinkt nicht

        game.run();
    }
}