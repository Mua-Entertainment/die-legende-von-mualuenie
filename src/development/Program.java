// Simo Münc

package development;
import engine.Game;
import engine.Settings;

public class Program {

    // Instanz der Datenbank-Klasse
    public static Database database;

    public static void main(String[] args) {

        database = new Database();
        database.setHighscore(1254);

        Settings settings = new Settings(10, 6, 100);
        Environment environment = new Environment();

        // Starten des Spiels
        Game game = new Game(settings, environment, "img\\ui\\icon.png");
        game.run();
    }
}