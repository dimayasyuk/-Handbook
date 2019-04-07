package commands.classes;

import commands.Command;
import constants.Blanks;
import dao.SectionDAO;
import entities.Content;
import entities.Section;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class NewClassCommand implements Command {
    private SectionDAO sectionDAO;

    public NewClassCommand(SectionDAO sectionDAO) {
        this.sectionDAO = sectionDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Section> listSection = sectionDAO.listSections();
        Content content = new Content();
        request.setAttribute("cont", content);
        request.setAttribute("sections", listSection);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Blanks.NEW_CLASS_PAGE);
        dispatcher.forward(request, response);
    }
}
