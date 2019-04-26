package servlets;

import commands.registration.GetRegistrationFormCommand;
import commands.registration.RegistrationCommand;
import dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class RegisterServlet extends HttpServlet {

    private UserDao userDao;

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            new GetRegistrationFormCommand().execute(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            new RegistrationCommand(userDao).execute(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        String jdbcDriver = getServletContext().getInitParameter("jdbcDriver");
        String jdbcURL = getServletContext().getInitParameter("jdbcURL");
        String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
        String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");

        userDao = new UserDao(jdbcDriver, jdbcURL, jdbcUsername, jdbcPassword);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
