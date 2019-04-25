package dao;

import connection.JDBC;
import entities.Role;
import entities.User;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private String jdbcDriver;
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;

    public UserDao(String jdbcDriver, String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public boolean insertUser(User user) throws SQLException, IOException {
        String sql = "INSERT INTO users (name, surname, login, password, roleId) VALUES (?, ?, ?, ?, ?)";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getSurname());
        statement.setString(3, user.getLogin());
        statement.setString(4, user.getPassword());
        statement.setInt(5, user.getRoleId());
        boolean row = (statement.executeUpdate()) > 0;

        statement.close();
        JDBC.closeConnection();

        return row;
    }

    public List<Role> getRoles() throws SQLException, IOException {
        List<Role> roles = new ArrayList<>();

        String sql = "SELECT * FROM role";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");

            Role role = new Role(id, name);
            roles.add(role);
        }

        resultSet.close();
        statement.close();

        JDBC.closeConnection();

        return roles;
    }

    public Role getRoleByName(String name) throws SQLException, IOException {
        Role role = null;
        String sql = "SELECT * FROM role WHERE name = ?";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            role = new Role(id, name);
        }

        resultSet.close();
        statement.close();
        JDBC.closeConnection();

        return role;
    }

    public Role getRoleById(int id) throws SQLException, IOException {
        Role role = null;
        String sql = "SELECT * FROM role WHERE id = ?";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            role = new Role(id, name);
        }

        resultSet.close();
        statement.close();
        JDBC.closeConnection();

        return role;
    }

    public List<User> getBulkOfUsers(int page, int recordsPerPage) throws SQLException, IOException {
        List<User> users = new ArrayList<>();

        String sql = "SELECT * FROM users limit " + page + ", " + recordsPerPage;

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            int roleId = resultSet.getInt("roleId");

            User user = new User(id, name, surname, login, password, roleId);
            users.add(user);
        }

        resultSet.close();
        statement.close();

        JDBC.closeConnection();

        return users;
    }

    public boolean deleteUser(int id) throws SQLException, IOException {
        String sql = "DELETE FROM users where id = ?";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        boolean count = statement.executeUpdate() > 0;

        statement.close();
        JDBC.closeConnection();

        return count;
    }

    public boolean updateUser(int id, int roleId) throws SQLException, IOException {
        String sql = "UPDATE users SET roleId = ? WHERE id = ?";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, roleId);
        statement.setInt(2, id);
        boolean count = statement.executeUpdate() > 0;

        statement.close();
        JDBC.closeConnection();

        return count;
    }

    public User getUserByLogin(String login) throws SQLException, IOException {
        String sql = "SELECT * FROM users WHERE login = ?";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);

        ResultSet resultSet = statement.executeQuery();
        User user = null;

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String password = resultSet.getString("password");
            int roleId = resultSet.getInt("roleId");

            user = new User(id, name, surname, login, password, roleId);
        }

        resultSet.close();
        statement.close();
        JDBC.closeConnection();

        return user;
    }

    public User getUserByLoginAndPassword(String login, String pass) throws SQLException, IOException {
        String sql = "SELECT * FROM users WHERE login = ? and password = ?";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, login);
        statement.setString(2, pass);

        ResultSet resultSet = statement.executeQuery();
        User user = null;

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String password = resultSet.getString("password");
            int roleId = resultSet.getInt("roleId");

            user = new User(id, name, surname, login, password, roleId);
        }

        resultSet.close();
        statement.close();
        JDBC.closeConnection();

        return user;
    }

    public int countUsers() throws IOException, SQLException {
        String sql = "SELECT COUNT(*) as count FROM users";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet.getInt("count");
        }

        return 0;
    }

    public User getLastUser() throws SQLException, IOException {
        String sql = "SELECT * FROM users ORDER BY id DESC LIMIT 1;";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        User user = null;

        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            String login = resultSet.getString("login");
            String password = resultSet.getString("password");
            int roleId = resultSet.getInt("roleId");

            user = new User(id, name, surname, login, password, roleId);
        }

        resultSet.close();
        statement.close();

        statement.close();
        JDBC.closeConnection();

        return user;
    }
}
