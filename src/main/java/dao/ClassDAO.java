package dao;

import connection.JDBC;
import entities.Class;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClassDAO {
    private String jdbcDriver;
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;

    public ClassDAO(String jdbcDriver, String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public boolean insertClass(Class cls) throws SQLException, IOException {
        String sql = "INSERT INTO class (name, description, created, modified, modifierId, contentId) VALUES (?, ?, ?, ?, ?, ?)";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cls.getName());
        statement.setString(2, cls.getDescription());
        statement.setDate(3, cls.getCreated());
        statement.setDate(4, cls.getModified());
        statement.setInt(5, cls.getModifierId());
        statement.setInt(6, cls.getContentId());
        boolean row = (statement.executeUpdate()) > 0;

        statement.close();
        JDBC.closeConnection();

        return row;
    }

    public List<Class> listAllClasses(int page, int recordsPerPage) throws SQLException, IOException {
        List<Class> listClass = new ArrayList<>();

        String sql = "SELECT * FROM class limit " + page + ", " + recordsPerPage;

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Date created = resultSet.getDate("created");
            Date modified = resultSet.getDate("modified");
            int modifiedId = resultSet.getInt("modifierId");
            int contentId = resultSet.getInt("contentId");

            Class cls = new Class(id, name, description, created, modified, modifiedId, contentId);
            listClass.add(cls);
        }

        resultSet.close();
        statement.close();

        JDBC.closeConnection();

        return listClass;
    }

    public List<Class> listClassesById(int page, int recordsPerPage, int sectionId) throws SQLException, IOException {
        List<Class> listClass = new ArrayList<>();

        String sql = "SELECT * FROM class where modifierId=" + sectionId + " limit " + page + ", " + recordsPerPage;

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Date created = resultSet.getDate("created");
            Date modified = resultSet.getDate("modified");
            int modifierId = resultSet.getInt("modifierId");
            int contentId = resultSet.getInt("contentId");

            Class cls = new Class(id, name, description, created, modified, modifierId, contentId);
            listClass.add(cls);
        }

        resultSet.close();
        statement.close();

        JDBC.closeConnection();

        return listClass;
    }

    public Class getClassById(int id) throws SQLException, IOException {
        Class cls = null;
        String sql = "SELECT * FROM class WHERE id = ?";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Date created = resultSet.getDate("created");
            Date modified = resultSet.getDate("modified");
            int modifierId = Integer.valueOf(resultSet.getString("modifierId"));
            int contentId = resultSet.getInt("contentId");

            cls = new Class(id, name, description, created, modified, modifierId, contentId);
        }

        resultSet.close();
        statement.close();

        return cls;
    }

    public boolean deleteClass(Class cls) throws SQLException, IOException {
        String sql = "DELETE FROM class where id = ?";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, cls.getId());
        boolean count = statement.executeUpdate() > 0;

        statement.close();
        JDBC.closeConnection();

        return count;
    }

    public boolean updateClass(Class cls) throws SQLException, IOException {
        String sql = "UPDATE class SET name = ?, description = ?, created = ?, modified = ?, modifierId = ?  WHERE id = ?";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cls.getName());
        statement.setString(2, cls.getDescription());
        statement.setDate(3, cls.getCreated());
        statement.setDate(4, cls.getModified());
        statement.setInt(5, cls.getModifierId());
        statement.setInt(6, cls.getId());
        boolean count = statement.executeUpdate() > 0;

        statement.close();
        JDBC.closeConnection();

        return count;
    }

    public int countClassesById(int id) throws IOException, SQLException {
        String sql = "SELECT COUNT(*) as count FROM class where modifierId=" + id;

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet.getInt("count");
        }

        return 0;
    }
}
