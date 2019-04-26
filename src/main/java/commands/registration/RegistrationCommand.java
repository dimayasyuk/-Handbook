package commands.registration;

import commands.Command;
import constants.Blanks;
import constants.Constants;
import constants.URL;
import dao.UserDao;
import encrypt.PasswordEncryptor;
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

public class RegistrationCommand implements Command {
    private UserDao userDao;


    public RegistrationCommand(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        User user = new User(name, surname, login, PasswordEncryptor.encrypt(password));

        String usedRole = userDao.countUsers() == 0 ? Constants.ADMIN_ROLE : Constants.USER_ROLE;
        Role role = setUserRole(user, usedRole);

        if(Objects.isNull(userDao.getUserByLogin(login))){
            HttpSession session = request.getSession();
            userDao.insertUser(user);
            session.setAttribute("user", userDao.getLastUser());
            session.setAttribute("role", role.getName());
            String page = role.getName().equals(Constants.ADMIN_ROLE) ? URL.LIST_USER : URL.LIST_SECTION;
            response.sendRedirect(Blanks.BASE_URL + page);
        }else {
            PrintWriter out = response.getWriter();
            out.println("<script type=\"text/javascript\">alert('User with such login is already exist.');location='register';</script>");
        }
    }

    private Role setUserRole(User user, String roleName) throws IOException, SQLException {
        Role role = userDao.getRoleByName(roleName);
        user.setRoleId(role.getId());
        return role;
    }
}
