package commands.login;

import commands.Command;
import constants.Blanks;
import constants.Constants;
import constants.URL;
import dao.UserDao;
import entities.Role;
import entities.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Objects;

public class LoginCommand implements Command {
    private UserDao userDao;

    public LoginCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        User user = userDao.getUserByLoginAndPassword(login, password);

        if (Objects.nonNull(user)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            Role role = userDao.getRoleById(user.getRoleId());
            session.setAttribute("role", role.getName());
            String page = role.getName().equals(Constants.ADMIN_ROLE) ? URL.LIST_USER : URL.LIST_SECTION;
            response.sendRedirect(Blanks.BASE_URL + page);
        } else {
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">alert('User with such login doesnt exist.');location='login';</script>");
        }
    }
}
