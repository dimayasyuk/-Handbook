package servlets;

import commands.Command;
import commands.section.*;
import constants.URL;
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

public class SectionServlet extends HttpServlet {

    private SectionDAO sectionDAO;
    private Map<String, Command> sectionCommands;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getRequestURI();

        try {
            sectionCommands.getOrDefault(action, sectionCommands.get(Blanks.BASE_URL + URL.LIST_SECTION)).execute(req, resp);
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

        sectionDAO = new SectionDAO(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
        initCommands();

    }

    public void setSectionDAO(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
        initCommands();
    }

    private void initCommands() {
        sectionCommands = new LinkedHashMap<>();
        sectionCommands.put(Blanks.BASE_URL + URL.NEW_SECTION, new NewSectionCommand());
        sectionCommands.put(Blanks.BASE_URL + URL.LIST_SECTION, new ListSectionCommand(sectionDAO));
        sectionCommands.put(Blanks.BASE_URL + URL.DELETE_SECTION, new DeleteSectionCommand(sectionDAO));
        sectionCommands.put(Blanks.BASE_URL + URL.EDIT_SECTION, new EditSectionCommand(sectionDAO));
        sectionCommands.put(Blanks.BASE_URL + URL.INSERT_SECTION, new InsertSectionCommand(sectionDAO));
        sectionCommands.put(Blanks.BASE_URL + URL.UPDATE_SECTION, new UpdateSectionCommand(sectionDAO));
    }
}
