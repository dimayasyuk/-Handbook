package commands.classes;

import commands.Command;
import dao.ClassDAO;
import entities.Class;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class UpdateClassCommand implements Command {
    private ClassDAO classDAO;

    public UpdateClassCommand(ClassDAO classDAO) {
        this.classDAO = classDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Date created = Date.valueOf(request.getParameter("created"));
        int modifierId = Integer.parseInt(request.getParameter("modifier"));

        Class cls = new Class(id, name, description, created, new Date(System.currentTimeMillis()), modifierId);
        classDAO.updateClass(cls);
        response.sendRedirect("list");
    }
}
