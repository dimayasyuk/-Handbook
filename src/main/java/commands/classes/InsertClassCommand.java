package commands.classes;

import commands.Command;
import dao.ClassDAO;
import entities.Class;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class InsertClassCommand implements Command {
    private ClassDAO classDAO;

    public InsertClassCommand(ClassDAO classDAO) {
        this.classDAO = classDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int modifierId = Integer.parseInt(request.getParameter("modifier"));
        Class newModifier = new Class(name, description, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()), modifierId);
        classDAO.insertClass(newModifier);
        response.sendRedirect("list");
    }
}
