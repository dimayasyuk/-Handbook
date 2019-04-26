package commands.registration;

import dao.UserDao;
import entities.Role;
import entities.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import servlets.LoginServlet;
import servlets.RegisterServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

public class RegistrationCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private HttpSession httpSession;
    private UserDao userDao;
    private User neededUser;

    @Before
    public void setUp() throws Exception {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
        httpSession = Mockito.mock(HttpSession.class);
        userDao = Mockito.mock(UserDao.class);
        Mockito.doAnswer((args) -> {
            if (args.getArgument(1) instanceof User) {
                neededUser = args.getArgument(1);
            }
            return null;
        }).when(httpSession).setAttribute(Mockito.any(), Mockito.any());
    }

    @Test
    public void execute() throws IOException, SQLException, ServletException {
        RegisterServlet registerServlet = new RegisterServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        User user = new User(1, "Nick", "Smith", "login", "password", 1);
        Role role = new Role(1, "admin");

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getParameter(eq("name"))).thenReturn(user.getName());
        Mockito.when(mockRequest.getParameter(eq("surname"))).thenReturn(user.getSurname());
        Mockito.when(mockRequest.getParameter(eq("login"))).thenReturn(user.getLogin());
        Mockito.when(mockRequest.getParameter(eq("password"))).thenReturn(user.getPassword());

        Mockito.doReturn(0).when(userDao).countUsers();
        Mockito.doReturn(role).when(userDao).getRoleByName(role.getName());
        Mockito.doReturn(null).when(userDao).getUserByLogin(user.getLogin());
        Mockito.doReturn(httpSession).when(mockRequest).getSession();
        Mockito.doReturn(true).when(userDao).insertUser(user);
        Mockito.doReturn(user).when(userDao).getLastUser();

        registerServlet.init();
        registerServlet.setUserDao(userDao);
        registerServlet.doPost(mockRequest, mockResponse);

        assertSame(neededUser.getLogin(), user.getLogin());
        assertSame(neededUser.getPassword(), user.getPassword());
    }
}