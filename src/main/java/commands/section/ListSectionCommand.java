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
import java.util.List;

public class ListSectionCommand implements Command {
    private SectionDAO sectionDAO;

    public ListSectionCommand(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int currentPage = Integer.valueOf(
                request.getParameter("currentPage") == null
                        ? "1"
                        : request.getParameter("currentPage")
        );
        int recordsPerPage = Integer.valueOf(
                request.getParameter("recordsPerPage") == null
                        ? "3"
                        : request.getParameter("recordsPerPage")
        );

        int noOfRecords = sectionDAO.countSections();

        int numberOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);

        List<Section> sections = sectionDAO.listAllSections((currentPage - 1) * recordsPerPage, recordsPerPage);
        request.setAttribute("noOfPages", numberOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);
        request.setAttribute("sections", sections);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Blanks.LIST_SECTION_PAGE);
        dispatcher.forward(request, response);
    }
}
