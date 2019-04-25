package servlets;

import commands.Command;
import commands.vkontakte.VkAccessTokenCommand;
import commands.vkontakte.VkAuthCommand;
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

public class VkServlet extends HttpServlet {
    private Map<String, Command> vkCommands;
    private UserDao userDao;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getRequestURI();

        try {
            vkCommands.getOrDefault(action, vkCommands.get(Blanks.BASE_URL + URL.VK_AUTHORIZATION)).execute(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
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
        vkCommands = new LinkedHashMap<>();
        vkCommands.put(Blanks.BASE_URL + URL.VK_AUTHORIZATION, new VkAuthCommand());
        vkCommands.put(Blanks.BASE_URL + URL.VK_TOKEN, new VkAccessTokenCommand(userDao));
    }
}
