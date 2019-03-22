package connection;


import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JDBC {

    private static Connection connection = null;

    private static void loadDriver(String jdbcDriver) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadConnection(String jdbcURL, String jdbcUsername, String jdbcPassword) throws IOException, SQLException {
        connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public static Connection connect(String jdbcDriver, String jdbcURL, String jdbcUsername, String jdbcPassword) throws IOException, SQLException {
        if (connection == null) {
            loadDriver(jdbcDriver);
            loadConnection(jdbcURL, jdbcUsername, jdbcPassword);
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                connection = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
