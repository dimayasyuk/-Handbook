package commands.modifier;

import commands.Command;
import dao.ModifierDAO;
import entities.Modifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class UpdateModifierCommand implements Command {
    private ModifierDAO modifierDAO;

    public UpdateModifierCommand(ModifierDAO modifierDAO) {
        this.modifierDAO = modifierDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        Date created = Date.valueOf(request.getParameter("created"));

        Modifier modifier = new Modifier(id, name, description, created, new Date(System.currentTimeMillis()));
        modifierDAO.updateModifier(modifier);
        response.sendRedirect("list");
    }
}
