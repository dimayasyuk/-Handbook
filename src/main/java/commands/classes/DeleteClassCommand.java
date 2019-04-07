package commands.classes;

import commands.Command;
import constants.Blanks;
import constants.URL;
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
        int section = Integer.parseInt(request.getParameter("section"));

        Class cls = new Class(id);
        classDAO.deleteClass(cls);
        response.sendRedirect(Blanks.BASE_URL + URL.LIST_CLASS + "?id=" + section);
    }
}
