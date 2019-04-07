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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import constants.Blanks;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

public class DeleteClassCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ClassDAO classDAO;
    private List<Class> neededClasses;
    private List<Class> initialClasses;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        classDAO = Mockito.mock(ClassDAO.class);
        Mockito.doAnswer((args) -> {
            Class deletedClass = args.getArgument(0);
            neededClasses = initialClasses.stream()
                    .filter(e -> (e.getId() != deletedClass.getId()))
                    .collect(Collectors.toList());
            return null;
        }).when(classDAO).deleteClass(Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void execute() throws ServletException, IOException {
        initialClasses = new LinkedList<>();
        Date date = new Date(System.currentTimeMillis());
        Class firstClass = new Class(1, "Command", "command", date, date, 1, 1);
        Class secondClass = new Class(2, "Singleton", "singleton", date, date, 1, 1);
        initialClasses.add(firstClass);
        initialClasses.add(secondClass);

        ClassServlet classServlet = new ClassServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.DELETE_CLASS);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(firstClass.getId()));
        Mockito.when(mockRequest.getParameter(eq("section"))).thenReturn(Integer.toString(firstClass.getContentId()));

        classServlet.init();
        classServlet.setClassDAO(classDAO);
        classServlet.doGet(mockRequest, mockResponse);

        assertEquals(neededClasses.size(), initialClasses.size() - 1);
        assertFalse(neededClasses.contains(firstClass));
    }
}