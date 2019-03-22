package servlets;

import commands.Command;
import commands.classes.*;
import commands.modifier.InsertModifierCommand;
import constants.URL;
import dao.ClassDAO;
import dao.ModifierDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

public class ClassServlet extends HttpServlet {
    private ClassDAO classDAO;
    private ModifierDAO modifierDAO;
    private Map<String, Command> classCommands;


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getRequestURI();

        try {
            classCommands.getOrDefault(action, classCommands.get(URL.LIST_CLASS)).execute(req, resp);
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

        classDAO = new ClassDAO(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
        modifierDAO = new ModifierDAO(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
        initCommands();
    }

    public void setClassDAO(ClassDAO classDAO) {
        this.classDAO = classDAO;
        initCommands();
    }

    public void setModifierDAO(ModifierDAO modifierDAO) {
        this.modifierDAO = modifierDAO;
        initCommands();
    }

    public void setDAO(ClassDAO classDAO, ModifierDAO modifierDAO) {
        this.classDAO = classDAO;
        this.modifierDAO = modifierDAO;
        initCommands();
    }

    private void initCommands() {
        classCommands = new LinkedHashMap<>();
        classCommands.put(URL.NEW_CLASS, new NewClassCommand(modifierDAO));
        classCommands.put(URL.LIST_CLASS, new ListClassCommand(modifierDAO, classDAO));
        classCommands.put(URL.DELETE_CLASS, new DeleteClassCommand(classDAO));
        classCommands.put(URL.EDIT_CLASS, new EditClassCommand(modifierDAO, classDAO));
        classCommands.put(URL.INSERT_CLASS, new InsertClassCommand(classDAO));
        classCommands.put(URL.UPDATE_CLASS, new UpdateClassCommand(classDAO));
    }
}
