package commands.section;

import commands.Command;
import constants.Blanks;
import constants.URL;
import dao.SectionDAO;
import entities.Section;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class InsertSectionCommand implements Command {
    private SectionDAO sectionDAO;

    public InsertSectionCommand(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Section section = new Section(name, description);
        sectionDAO.insertSection(section);
        response.sendRedirect(Blanks.BASE_URL +  URL.LIST_SECTION);
    }
}
