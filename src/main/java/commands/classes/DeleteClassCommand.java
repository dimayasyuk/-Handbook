package commands.classes;

import commands.Command;
import dao.ClassDAO;
import entities.Class;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteClassCommand implements Command {
    private ClassDAO classDAO;

    public DeleteClassCommand(ClassDAO classDAO) {
        this.classDAO = classDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));

        Class cls = new Class(id);
        classDAO.deleteClass(cls);
        response.sendRedirect("list");
    }
}
