// Simo Münc

package development;
import engine.Game;
import engine.Settings;

public class Program {

    public static Database database;
    public static DataFile data;

    public static void main(String[] args) {

        database = new Database();
        data = new DataFile();

        Settings settings = new Settings(10, 6, 100);
        Environment environment = new Environment();

        // Starten des Spiels
        Game game = new Game(settings, environment, "img\\ui\\icon.png");
        game.run();
    }
}