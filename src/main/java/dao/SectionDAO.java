package dao;

import connection.JDBC;
import entities.Section;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SectionDAO {
    private String jdbcDriver;
    private String jdbcURL;
    private String jdbcUsername;
    private String jdbcPassword;

    public SectionDAO(String jdbcDriver, String jdbcURL, String jdbcUsername, String jdbcPassword) {
        this.jdbcDriver = jdbcDriver;
        this.jdbcURL = jdbcURL;
        this.jdbcUsername = jdbcUsername;
        this.jdbcPassword = jdbcPassword;
    }

    public boolean insertSection(Section section) throws SQLException, IOException {
        String sql = "INSERT INTO section (name, description) VALUES (?, ?)";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, section.getName());
        statement.setString(2, section.getDescription());
        boolean row = (statement.executeUpdate()) > 0;

        statement.close();
        JDBC.closeConnection();

        return row;
    }

    public List<Section> listAllSections(int page, int recordsPerPage) throws SQLException, IOException {
        List<Section> sections = new ArrayList<>();


        String sql = "SELECT * FROM section limit " + page + ", " + recordsPerPage;

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");

            Section section = new Section(id, name, description);
            sections.add(section);
        }

        resultSet.close();
        statement.close();

        JDBC.closeConnection();

        return sections;
    }

    public List<Section> listSections() throws SQLException, IOException {
        List<Section> sections = new ArrayList<>();


        String sql = "SELECT * FROM section";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");

            Section section = new Section(id, name, description);
            sections.add(section);
        }

        resultSet.close();
        statement.close();

        JDBC.closeConnection();

        return sections;
    }

    public boolean deleteSection(Section section) throws SQLException, IOException {
        String sql = "DELETE FROM section where id = ?";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, section.getId());
        boolean count = statement.executeUpdate() > 0;

        statement.close();
        JDBC.closeConnection();

        return count;
    }

    public boolean updateSection(Section section) throws SQLException, IOException {
        String sql = "UPDATE section SET name = ?, description = ? WHERE id = ?";
        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, section.getName());
        statement.setString(2, section.getDescription());
        statement.setInt(3, section.getId());
        boolean count = statement.executeUpdate() > 0;

        statement.close();
        JDBC.closeConnection();

        return count;
    }

    public Section getSection(int id) throws SQLException, IOException {
        Section section = null;
        String sql = "SELECT * FROM section WHERE id = ?";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");

            section = new Section(id, name, description);
        }

        resultSet.close();
        statement.close();

        return section;
    }

    public int countSections() throws IOException, SQLException {
        String sql = "SELECT COUNT(*) as count FROM section";

        Connection connection = JDBC.connect(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        if(resultSet.next()){
            return resultSet.getInt("count");
        }

        return 0;
    }
}
