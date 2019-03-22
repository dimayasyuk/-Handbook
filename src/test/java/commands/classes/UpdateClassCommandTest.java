package commands.classes;

import constants.Blanks;
import constants.URL;
import dao.ClassDAO;
import entities.Class;
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
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

public class UpdateClassCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ClassDAO classDAO;
    private Class neededClass;
    private List<Class> initialClasses;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        classDAO = Mockito.mock(ClassDAO.class);
        Mockito.doAnswer((args) -> {
            neededClass = args.getArgument(0);
            return null;
        }).when(classDAO).updateClass(Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
        initialClasses = ClassService.initClasses();
    }

    @Test
    public void execute() throws IOException, SQLException, ServletException {
        ClassServlet classServlet = new ClassServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        Mockito.when(mockRequest.getRequestURI()).thenReturn(URL.UPDATE_CLASS);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Date date = new Date(System.currentTimeMillis());
        Class newClass = new Class(1, "Bridge", "class", date, date, 1);

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(newClass.getId()));
        Mockito.when(mockRequest.getParameter(eq("name"))).thenReturn(newClass.getName());
        Mockito.when(mockRequest.getParameter(eq("description"))).thenReturn(newClass.getDescription());
        Mockito.when(mockRequest.getParameter(eq("created"))).thenReturn(newClass.getCreated().toString());
        Mockito.when(mockRequest.getParameter(eq("modifier"))).thenReturn(Integer.toString(newClass.getModifierId()));

        classServlet.init();
        classServlet.setClassDAO(classDAO);
        classServlet.doGet(mockRequest, mockResponse);

        Optional<Class> matchingClass = initialClasses.stream().
                filter(cls -> cls.getId() == newClass.getId()).
                findFirst();

        assertFalse(neededClass.getName() == matchingClass.get().getName());
        assertTrue(neededClass.getDescription() == matchingClass.get().getDescription());
    }
}