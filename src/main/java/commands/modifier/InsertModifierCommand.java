package commands.modifier;

import commands.Command;
import dao.ModifierDAO;
import entities.Modifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class InsertModifierCommand implements Command {
    private ModifierDAO modifierDAO;

    public InsertModifierCommand(ModifierDAO modifierDAO) {
        this.modifierDAO = modifierDAO;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        Modifier newModifier = new Modifier(name, description, new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis()));
        modifierDAO.insertModifier(newModifier);
        response.sendRedirect("list");
    }
}
