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
import java.util.List;

public class ListModifierCommand implements Command {
    private ModifierDAO modifierDAO;

    public ListModifierCommand(ModifierDAO modifierDAO) {
        this.modifierDAO = modifierDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        List<Modifier> listModifier = modifierDAO.listAllModifiers();
        request.setAttribute("modifierList", listModifier);
        RequestDispatcher dispatcher = request.getRequestDispatcher(Blanks.LIST_MODIFIER_PAGE);
        dispatcher.forward(request, response);
    }
}
