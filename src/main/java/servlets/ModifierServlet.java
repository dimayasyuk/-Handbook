package servlets;

import commands.Command;
import commands.modifier.*;
import constants.URL;
import dao.ModifierDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ModifierServlet extends HttpServlet {

    private ModifierDAO modifierDAO;
    private Map<String, Command> modifierCommands;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        try {
            modifierCommands.getOrDefault(action, modifierCommands.get(URL.LIST_MODIFIER)).execute(req, resp);
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

        modifierDAO = new ModifierDAO(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
        initCommands();

    }

    public void setModifierDAO(ModifierDAO modifierDAO) {
        this.modifierDAO = modifierDAO;
        initCommands();
    }

    private void initCommands() {
        modifierCommands = new LinkedHashMap<>();
        modifierCommands.put(URL.NEW_MODIFIER, new NewModifierCommand());
        modifierCommands.put(URL.LIST_MODIFIER, new ListModifierCommand(modifierDAO));
        modifierCommands.put(URL.DELETE_MODIFIER, new DeleteModifierCommand(modifierDAO));
        modifierCommands.put(URL.EDIT_MODIFIER, new EditModifierCommand(modifierDAO));
        modifierCommands.put(URL.INSERT_MODIFIER, new InsertModifierCommand(modifierDAO));
        modifierCommands.put(URL.UPDATE_MODIFIER, new UpdateModifierCommand(modifierDAO));
    }
}
