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

public class UpdateClassCommand implements Command {
    private ClassDAO classDAO;
    private ContentDAO contentDAO;

    public UpdateClassCommand(ClassDAO classDAO, ContentDAO contentDAO) {
        this.classDAO = classDAO;
        this.contentDAO = contentDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Date created = Date.valueOf(request.getParameter("created"));
        int modifierId = Integer.parseInt(request.getParameter("section"));
        String text = request.getParameter("content");
        int contId = Integer.parseInt(request.getParameter("contId"));


        contentDAO.updateContent(new Content(contId, text));

        Class cls = new Class(id, name, description, created, new Date(System.currentTimeMillis()), modifierId, contId);
        classDAO.updateClass(cls);
        response.sendRedirect(Blanks.BASE_URL + URL.LIST_CLASS + "?id=" + modifierId);
    }
}
