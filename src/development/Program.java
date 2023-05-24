package development;

import engine.Game;
import engine.Settings;

public class Program {
    public static void main(String[] args) {
        Settings settings = new Settings(10, 6, 100);
        MainMenu menu = new MainMenu();

        Game game = new Game(settings, menu);
        game.run();
    }
}