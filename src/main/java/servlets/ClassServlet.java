package servlets;

import commands.Command;
import commands.classes.*;
import constants.URL;
import dao.ClassDAO;
import dao.ContentDAO;
import dao.SectionDAO;
import constants.Blanks;

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
    private SectionDAO sectionDAO;
    private ContentDAO contentDAO;
    private Map<String, Command> classCommands;


    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getRequestURI();

        try {
            classCommands.getOrDefault(action, classCommands.get(Blanks.BASE_URL + URL.LIST_CLASS)).execute(req, resp);
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

        classDAO = new ClassDAO(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
        sectionDAO = new SectionDAO(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
        contentDAO = new ContentDAO(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
        initCommands();
    }

    public void setClassDAO(ClassDAO classDAO) {
        this.classDAO = classDAO;
        initCommands();
    }

    public void setContentDAO(ContentDAO contentDAO) {
        this.contentDAO = contentDAO;
        initCommands();
    }

    public void setModifierDAO(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
        initCommands();
    }

    public void setDAO(ClassDAO classDAO, SectionDAO sectionDAO) {
        this.classDAO = classDAO;
        this.sectionDAO = sectionDAO;
        initCommands();
    }

    private void initCommands() {
        classCommands = new LinkedHashMap<>();
        classCommands.put(Blanks.BASE_URL + URL.NEW_CLASS, new NewClassCommand(sectionDAO));
        classCommands.put(Blanks.BASE_URL + URL.SHOW_CLASS, new ShowClassCommand(classDAO, contentDAO, sectionDAO));
        classCommands.put(Blanks.BASE_URL + URL.LIST_CLASS, new ListClassCommand(sectionDAO, classDAO));
        classCommands.put(Blanks.BASE_URL + URL.DELETE_CLASS, new DeleteClassCommand(classDAO));
        classCommands.put(Blanks.BASE_URL + URL.EDIT_CLASS, new EditClassCommand(sectionDAO, classDAO, contentDAO));
        classCommands.put(Blanks.BASE_URL + URL.INSERT_CLASS, new InsertClassCommand(classDAO, contentDAO));
        classCommands.put(Blanks.BASE_URL + URL.UPDATE_CLASS, new UpdateClassCommand(classDAO, contentDAO));
    }
}
