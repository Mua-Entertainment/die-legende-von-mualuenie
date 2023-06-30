package development;

import engine.SafeList;
import java.sql.*;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class Database {
    private final String HOST = "sql7.freesqldatabase.com";
    private final String USER = "sql7628410";
    private final String PASSWORD = "ua2Jkhhx4J";
    private final String PORT = "3306";
    private final String CONNECTION_URL = "jdbc:mysql://" + HOST + ':' + PORT + '/' + USER;

    public Database() {
        // trägt offline gespeicherte Dateien in die Datenbank ein
        setHighscore(Program.data.getHighscore(), Program.data.getDate());
        setName(Program.data.getName());
    }

    private void connect(Consumer<Connection> func) {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
            if (con != null) {
                func.accept(con);
            }
        } catch (Exception e) {
            System.out.println(e.getClass().getTypeName());
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.getClass().getTypeName());
            }
        }
    }

    public void setHighscore(int value, long date) {
        connect(con -> {
            try {
                Program.data.setHighscore(value);
                Program.data.setDate(date);

                Statement stmt = con.createStatement();

                stmt.executeUpdate("DELETE FROM users WHERE id='" + Program.data.getUID() + '\'');
                stmt.executeUpdate("INSERT INTO users (id, name, highscore, date) VALUE (" + Program.data.getUID() + ", '" + Program.data.getName() + "', " + value + ", " + date + ")");
            } catch (Exception e) {
                System.out.println(e.getClass().getTypeName());
            }
        });
    }

    public void setName(String name) {
        connect(con -> {
            try {
                Program.data.setName(name);

                Statement stmt = con.createStatement();

                stmt.executeUpdate("DELETE FROM users WHERE id=" + Program.data.getUID());
                stmt.executeUpdate("INSERT INTO users (id, name, highscore, date) VALUE (" + Program.data.getUID() + ", '" + name + "', " + Program.data.getHighscore() + ", " + Program.data.getDate()+ ")");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e.getClass().getTypeName());
            }
        });
    }

    public SafeList<User> getSortedHighscores() throws SQLException {
        SafeList<User> result = new SafeList<>();
        AtomicBoolean connected = new AtomicBoolean(false);

        connect(con -> {
            try {
                String query = "SELECT * FROM users ORDER BY highscore DESC";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    User user = new User(rs.getString("id"), rs.getString("name"), rs.getInt("highscore"), rs.getLong("date"));
                    result.add(user);
                }
            } catch (SQLException e) {
                System.out.println(e.getClass().getTypeName());
            }

            connected.set(true);
        });

        if (!connected.get()) {
            throw new SQLException("database connection failed");
        }

        return result;
    }
}