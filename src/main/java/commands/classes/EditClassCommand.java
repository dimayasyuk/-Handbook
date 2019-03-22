package commands.classes;

import commands.Command;
import constants.Blanks;
import dao.ClassDAO;
import dao.ModifierDAO;
import entities.Class;
import entities.Modifier;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditClassCommand implements Command {
    private ModifierDAO modifierDAO;
    private ClassDAO classDAO;

    public EditClassCommand(ModifierDAO modifierDAO, ClassDAO classDAO) {
        this.modifierDAO = modifierDAO;
        this.classDAO = classDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Class existingClass = classDAO.getClassById(id);
        List<Modifier> listModifier = modifierDAO.listAllModifiers();
        RequestDispatcher dispatcher = request.getRequestDispatcher(Blanks.NEW_CLASS_PAGE);
        request.setAttribute("modifiers", listModifier);
        request.setAttribute("cls", existingClass);
        dispatcher.forward(request, response);
    }
}
