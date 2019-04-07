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

public class DeleteSectionCommand implements Command {
    private SectionDAO sectionDAO;

    public DeleteSectionCommand(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));

        Section section = new Section(id);
        sectionDAO.deleteSection(section);
        response.sendRedirect(Blanks.BASE_URL + URL.LIST_SECTION);
    }
}
