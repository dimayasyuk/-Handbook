package commands.modifier;

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

public class EditModifierCommand implements Command {
    private ModifierDAO modifierDAO;

    public EditModifierCommand(ModifierDAO modifierDAO) {
        this.modifierDAO = modifierDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Modifier existingModifier = modifierDAO.getModifier(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Blanks.NEW_MODIFIER_PAGE);
        request.setAttribute("modifier", existingModifier);
        dispatcher.forward(request, response);
    }
}
