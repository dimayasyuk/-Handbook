package commands.classes;

import commands.Command;
import constants.Blanks;
import dao.ModifierDAO;
import entities.Modifier;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class NewClassCommand implements Command {
    private ModifierDAO modifierDAO;

    public NewClassCommand(ModifierDAO modifierDAO) {
        this.modifierDAO = modifierDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Modifier> listModifier = modifierDAO.listAllModifiers();
        request.setAttribute("modifiers", listModifier);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Blanks.NEW_CLASS_PAGE);
        dispatcher.forward(request, response);
    }
}
