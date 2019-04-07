package commands.classes;

import commands.Command;
import constants.Blanks;
import constants.URL;
import dao.ClassDAO;
import dao.ContentDAO;
import entities.Class;
import entities.Content;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class InsertClassCommand implements Command {
    private ClassDAO classDAO;
    private ContentDAO contentDAO;

    public InsertClassCommand(ClassDAO classDAO, ContentDAO contentDAO) {
        this.classDAO = classDAO;
        this.contentDAO = contentDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int modifierId = Integer.parseInt(request.getParameter("section"));
        String text = request.getParameter("content");
        contentDAO.insertContent(new Content(text));
        Content content = contentDAO.getLastContent();
        Class newClass = new Class(name, description, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), modifierId, content.getId());
        classDAO.insertClass(newClass);
        response.sendRedirect(Blanks.BASE_URL + URL.LIST_CLASS + "?id=" + modifierId);
    }
}
