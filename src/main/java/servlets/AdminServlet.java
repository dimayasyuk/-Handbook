package servlets;

import commands.Command;
import commands.admin.GetHomeAdminPageCommand;
import constants.Blanks;
import constants.URL;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class AdminServlet  extends HttpServlet {
    private UserDao userDao;
    private Map<String, Command> commands;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        try {
            commands.getOrDefault(action, commands.get(Blanks.BASE_URL + URL.LIST_USER)).execute(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    @Override
    public void init() {
        String jdbcDriver = getServletContext().getInitParameter("jdbcDriver");
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        userDao = new UserDao(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
        initCommands();
    }

    private void initCommands() {
        commands = new LinkedHashMap<>();
        commands.put(Blanks.BASE_URL + URL.LIST_USER, new GetHomeAdminPageCommand());

    }
}
