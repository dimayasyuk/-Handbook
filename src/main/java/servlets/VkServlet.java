package servlets;

import commands.Command;
import commands.vkontakte.VkAccessTokenCommand;
import commands.vkontakte.VkAuthCommand;
import constants.Blanks;
import constants.URL;

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

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String action = Blanks.BASE_URL + req.getRequestURI();

        try {
            vkCommands.getOrDefault(action, vkCommands.get(URL.VK_AUTHORIZATION)).execute(req, resp);
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
        initCommands();
    }

    private void initCommands() {
        vkCommands = new LinkedHashMap<>();
        vkCommands.put(Blanks.BASE_URL + URL.VK_AUTHORIZATION, new VkAuthCommand());
        vkCommands.put(Blanks.BASE_URL + URL.VK_TOKEN, new VkAccessTokenCommand());
    }
}
