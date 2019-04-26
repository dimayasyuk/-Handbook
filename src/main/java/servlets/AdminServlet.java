package servlets;

import commands.Command;
import commands.admin.DeleteUserCommand;
import commands.admin.GetHomeAdminPageCommand;
import commands.admin.UpdateUserCommand;
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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        try {
            commands.getOrDefault(action, commands.get(Blanks.BASE_URL + URL.LIST_USER)).execute(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
        initCommands();
    }

    private void initCommands() {
        commands = new LinkedHashMap<>();
        commands.put(Blanks.BASE_URL + URL.LIST_USER, new GetHomeAdminPageCommand(userDao));
        commands.put(Blanks.BASE_URL + URL.DELETE_USER, new DeleteUserCommand(userDao));
        commands.put(Blanks.BASE_URL + URL.UPDATE_USER, new UpdateUserCommand(userDao));

    }
}
