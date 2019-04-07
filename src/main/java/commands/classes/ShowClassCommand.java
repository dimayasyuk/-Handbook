package commands.classes;

import commands.Command;
import constants.Blanks;
import dao.ClassDAO;
import dao.ContentDAO;
import dao.SectionDAO;
import entities.Class;
import entities.Content;
import entities.Section;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ShowClassCommand implements Command {
    private ClassDAO classDAO;
    private ContentDAO contentDAO;

    public ShowClassCommand(ClassDAO classDAO, ContentDAO contentDAO, SectionDAO sectionDAO) {
        this.classDAO = classDAO;
        this.contentDAO = contentDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Class existingClass = classDAO.getClassById(id);
        Content content = contentDAO.getContentById(existingClass.getContentId());
        RequestDispatcher dispatcher = request.getRequestDispatcher(Blanks.DETAIL_CLASS_PAGE);
        request.setAttribute("cont", content);
        request.setAttribute("cls", existingClass);
        dispatcher.forward(request, response);

    }
}
