package commands.login;

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

public class LoginCommandTest {
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
        Mockito.doAnswer((args) -> {
            if (args.getArgument(1) instanceof User) {
                neededUser = args.getArgument(1);
            }
            return null;
        }).when(httpSession).setAttribute(Mockito.any(), Mockito.any());
        userDao = Mockito.mock(UserDao.class);
    }

    @Test
    public void execute() throws IOException, SQLException, ServletException {
        LoginServlet loginServlet = new LoginServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        User user = new User(1, "Nick", "Smith", "login", "password", 1);
        Role role = new Role(1, "admin");

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getParameter(eq("login"))).thenReturn(user.getLogin());
        Mockito.when(mockRequest.getParameter(eq("password"))).thenReturn(user.getPassword());
        Mockito.doReturn(user).when(userDao).getUserByLoginAndPassword(user.getLogin(), user.getPassword());
        Mockito.doReturn(role).when(userDao).getRoleById(user.getRoleId());
        Mockito.doReturn(httpSession).when(mockRequest).getSession();

        loginServlet.init();
        loginServlet.setUserDao(userDao);
        loginServlet.doPost(mockRequest, mockResponse);

        assertSame(neededUser.getLogin(), user.getLogin());
        assertSame(neededUser.getPassword(), user.getPassword());
    }
}