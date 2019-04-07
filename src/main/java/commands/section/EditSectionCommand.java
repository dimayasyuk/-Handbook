package commands.section;

import commands.Command;
import constants.Blanks;
import dao.SectionDAO;
import entities.Section;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class EditSectionCommand implements Command {
    private SectionDAO sectionDAO;

    public EditSectionCommand(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Section existingSection = sectionDAO.getSection(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Blanks.NEW_SECTION_PAGE);
        request.setAttribute("section", existingSection);
        dispatcher.forward(request, response);
    }
}
