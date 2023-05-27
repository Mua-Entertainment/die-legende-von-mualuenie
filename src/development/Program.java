// Simo Münc

package development;
import engine.Game;
import engine.Settings;

import java.sql.Connection;
import java.sql.DriverManager;

public class Program {
    public static void main(String[] args) throws ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", "mualuenie");
        } catch (Exception ex) {
        }

        Settings settings = new Settings(10, 6, 100);
        Environment environment = new Environment();

        // Starten des Spiels
        Game game = new Game(settings, environment, "img\\ui\\icon.png");
        game.run();
    }
}