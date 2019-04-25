package commands.admin;

import commands.Command;
import constants.Blanks;
import constants.URL;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateUserCommand implements Command {
    private UserDao userDao;

    public UpdateUserCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        int role = Integer.parseInt(request.getParameter("rol"));
        userDao.updateUser(id, role);
        response.sendRedirect(Blanks.BASE_URL + URL.LIST_USER);
    }
}
