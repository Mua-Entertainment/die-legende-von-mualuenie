// Simo Münc

package development;

import engine.Game;
import engine.Settings;

import java.sql.*;

public class Program {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/jdbcdemo";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from highscore");

            while (resultSet.next()) {
                System.out.println(resultSet.getString(1) + " " + resultSet.getFloat(2));
            }

            connection.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Settings settings = new Settings(10, 6, 100);
        Environment environment = new Environment();

        // Starten des Spiels
        Game game = new Game(settings, environment, "img\\ui\\icon.png");
        game.run();
    }
}