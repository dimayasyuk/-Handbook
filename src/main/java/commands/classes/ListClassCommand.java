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

public class ListClassCommand implements Command {
    private ModifierDAO modifierDAO;
    private ClassDAO classDAO;

    public ListClassCommand(ModifierDAO modifierDAO, ClassDAO classDAO) {
        this.modifierDAO = modifierDAO;
        this.classDAO = classDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Class> listClasses = classDAO.listAllClasses();
        List<Modifier> listModifier = modifierDAO.listAllModifiers();
        request.setAttribute("modifiers", listModifier);
        request.setAttribute("clsList", listClasses);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Blanks.LIST_CLASS_PAGE);
        dispatcher.forward(request, response);
    }
}
