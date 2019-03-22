package commands.classes;

import constants.URL;
import dao.ClassDAO;
import entities.Class;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import servlets.ClassServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

public class InsertClassCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ClassDAO classDAO;
    private Class neededClass;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        classDAO = Mockito.mock(ClassDAO.class);
        Mockito.doAnswer((args) -> {
            neededClass = args.getArgument(0);
            return null;
        }).when(classDAO).insertClass(Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void execute() throws ServletException, IOException {
        ClassServlet classServlet = new ClassServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        Mockito.when(mockRequest.getRequestURI()).thenReturn(URL.INSERT_CLASS);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Date date = new Date(System.currentTimeMillis());
        Class insertClass = new Class(1, "Command", "command", date, date, 1);

        Mockito.when(mockRequest.getParameter(eq("name"))).thenReturn(insertClass.getName());
        Mockito.when(mockRequest.getParameter(eq("description"))).thenReturn(insertClass.getDescription());
        Mockito.when(mockRequest.getParameter(eq("modifier"))).thenReturn(Integer.toString(insertClass.getModifierId()));

        classServlet.init();
        classServlet.setClassDAO(classDAO);
        classServlet.doGet(mockRequest, mockResponse);

        assertTrue(neededClass.getDescription() == insertClass.getDescription());
        assertTrue(neededClass.getName() == insertClass.getName());
    }
}