package dao;

import connection.JDBC;
import entities.Modifier;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ModifierDAO {
    private String jdbcDriver;
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;

    public ModifierDAO(String jdbcDriver, String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public boolean insertModifier(Modifier modifier) throws SQLException, IOException {
        String sql = "INSERT INTO modifier (name, description, created, modified) VALUES (?, ?, ?, ?)";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, modifier.getName());
        statement.setString(2, modifier.getDescription());
        statement.setDate(3, modifier.getCreated());
        statement.setDate(4, modifier.getModified());
        boolean row = (statement.executeUpdate()) > 0;

        statement.close();
        JDBC.closeConnection();

        return row;
    }

    public List<Modifier> listAllModifiers() throws SQLException, IOException {
        List<Modifier> listModifier = new ArrayList<>();

        String sql = "SELECT * FROM modifier";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Date created = resultSet.getDate("created");
            Date modified = resultSet.getDate("modified");

            Modifier modifier = new Modifier(id, name, description, created, modified);
            listModifier.add(modifier);
        }

        resultSet.close();
        statement.close();

        JDBC.closeConnection();

        return listModifier;
    }

    public boolean deleteModifier(Modifier modifier) throws SQLException, IOException {
        String sql = "DELETE FROM modifier where id = ?";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, modifier.getId());
        boolean count = statement.executeUpdate() > 0;

        statement.close();
        JDBC.closeConnection();

        return count;
    }

    public boolean updateModifier(Modifier modifier) throws SQLException, IOException {
        String sql = "UPDATE modifier SET name = ?, description = ?, created = ?, modified = ? WHERE id = ?";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, modifier.getName());
        statement.setString(2, modifier.getDescription());
        statement.setDate(3, modifier.getCreated());
        statement.setDate(4, modifier.getModified());
        statement.setInt(5, modifier.getId());
        boolean count = statement.executeUpdate() > 0;

        statement.close();
        JDBC.closeConnection();

        return count;
    }

    public Modifier getModifier(int id) throws SQLException, IOException {
        Modifier modifier = null;
        String sql = "SELECT * FROM modifier WHERE id = ?";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Date created = resultSet.getDate("created");
            Date modified = resultSet.getDate("modified");

            modifier = new Modifier(id, name, description, created, modified);
        }

        resultSet.close();
        statement.close();

        return modifier;
    }

    public void cleanDatabase() throws IOException, SQLException {
        String sql = "DELETE FROM modifier";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();

        statement.close();
        JDBC.closeConnection();
    }
}
