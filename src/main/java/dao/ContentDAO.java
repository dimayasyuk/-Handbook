package dao;

import connection.JDBC;
import entities.Content;

import java.io.IOException;
import java.sql.*;

public class ContentDAO {
    private String jdbcDriver;
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;

    public ContentDAO(String jdbcDriver, String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public boolean insertContent(Content content) throws SQLException, IOException {
        String sql = "INSERT INTO content (text) VALUES (?)";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, content.getText());

        boolean row = (statement.executeUpdate()) > 0;

        statement.close();
        JDBC.closeConnection();

        return row;
    }

    public Content getContentById(int id) throws SQLException, IOException {
        Content content = null;
        String sql = "SELECT * FROM content WHERE id = ?";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String text = resultSet.getString("text");

            content = new Content(id, text);
        }

        resultSet.close();
        statement.close();

        return content;
    }

    public boolean updateContent(Content content) throws SQLException, IOException {
        String sql = "UPDATE content SET text = ? WHERE id = ?";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, content.getText());
        statement.setInt(2, content.getId());
        boolean count = statement.executeUpdate() > 0;

        statement.close();
        JDBC.closeConnection();

        return count;
    }

    public Content getLastContent() throws SQLException, IOException {
        String sql = "SELECT * FROM content ORDER BY id DESC LIMIT 1;";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        Content content = null;

        if (resultSet.next()) {
            int id = Integer.valueOf(resultSet.getString("id"));
            String text = resultSet.getString("text");
            content = new Content(id, text);
        }

        resultSet.close();
        statement.close();

        statement.close();
        JDBC.closeConnection();

        return content;
    }
}
