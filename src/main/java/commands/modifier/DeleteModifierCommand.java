package commands.modifier;

import commands.Command;
import dao.ModifierDAO;
import entities.Modifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteModifierCommand implements Command {
    private ModifierDAO modifierDAO;

    public DeleteModifierCommand(ModifierDAO modifierDAO) {
        this.modifierDAO = modifierDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));

        Modifier modifier = new Modifier(id);
        modifierDAO.deleteModifier(modifier);
        response.sendRedirect("list");
    }
}
