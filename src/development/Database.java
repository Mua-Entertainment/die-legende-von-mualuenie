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

    public void setHighscore(int value) {
        connect(con -> {
            try {
                Program.data.setHighscore(value);

                Statement stmt = con.createStatement();

                stmt.executeUpdate("DELETE FROM highscores WHERE id='" + Program.data.getUUID() + '\'');
                stmt.executeUpdate("INSERT INTO highscores (id, value) VALUE ('" + Program.data.getUUID() + "', " + value + ")");
            } catch (Exception e) {
                System.out.println(e.getClass().getTypeName());
            }
        });
    }

    public int getHighscore() {
        return Program.data.getHighscore();
    }

    public SafeList<Integer> getSortedHighscores() throws SQLException {
        SafeList<Integer> result = new SafeList<>();
        AtomicBoolean connected = new AtomicBoolean(false);

        connect(con -> {
            try {
                String query = "SELECT * FROM highscores";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    result.add(rs.getInt("value"));
                }
            } catch (SQLException e) {
                System.out.println(e.getClass().getTypeName());
            }

            connected.set(true);
        });

        if (!connected.get()) {
            throw new SQLException("database connection failed");
        }

        result.sort(Collections.reverseOrder());
        return result;
    }
}