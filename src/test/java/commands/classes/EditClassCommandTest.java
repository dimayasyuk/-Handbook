package commands.classes;

import constants.Blanks;
import constants.URL;
import dao.ClassDAO;
import dao.ModifierDAO;
import entities.Class;
import entities.Modifier;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import servlets.ClassServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

public class EditClassCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ClassDAO classDAO;
    private ModifierDAO modifierDAO;
    private Object neededClass;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        classDAO = Mockito.mock(ClassDAO.class);
        modifierDAO = Mockito.mock(ModifierDAO.class);
        Mockito.doAnswer((args) -> {
            neededClass = args.getArgument(1);
            return null;
        }).when(mockRequest).setAttribute(Mockito.any(), Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void execute() throws IOException, SQLException, ServletException {
        ClassServlet classServlet = new ClassServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };
        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(mockRequest.getRequestURI()).thenReturn(URL.EDIT_CLASS);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Date date = new Date(System.currentTimeMillis());
        Class insertClass = new Class(1, "Command", "command", date, date, 1);

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(insertClass.getId()));
        Mockito.doReturn(insertClass).when(classDAO).getClassById(insertClass.getId());

        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.NEW_CLASS_PAGE))).thenReturn(requestDispatcher);

        classServlet.init();
        classServlet.setDAO(classDAO, modifierDAO);
        classServlet.doGet(mockRequest, mockResponse);

        assertEquals(neededClass, insertClass);
    }
}