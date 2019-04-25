package servlets;

import commands.Command;
import commands.github.GithubAccessTokenCommand;
import commands.github.GithubAuthCommand;
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

public class GithubServlet extends HttpServlet {
    private Map<String, Command> githubCommands;
    private UserDao userDao;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = req.getRequestURI();

        try {
            githubCommands.getOrDefault(action, githubCommands.get(Blanks.BASE_URL + URL.GITHUB_AUTHORIZATION)).execute(req, resp);
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
        githubCommands = new LinkedHashMap<>();
        githubCommands.put(Blanks.BASE_URL + URL.GITHUB_AUTHORIZATION, new GithubAuthCommand());
        githubCommands.put(Blanks.BASE_URL + URL.GITHUB_TOKEN, new GithubAccessTokenCommand(userDao));
    }
}
