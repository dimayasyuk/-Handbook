package commands.classes;

import constants.Blanks;
import constants.URL;
import dao.ClassDAO;
import entities.Class;
import entities.Section;
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
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

public class ListClassCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ClassDAO classDAO;
    private List<Object> neededClasses;
    private List<Class> initialClasses;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        classDAO = Mockito.mock(ClassDAO.class);
        Mockito.doAnswer((args) -> {
            if (args.getArgument(1) instanceof List<?>) {
                neededClasses = args.getArgument(1);
            }
            return null;
        }).when(mockRequest).setAttribute(Mockito.any(), Mockito.any());
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
        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
        Section section = new Section(1,"C++", "C++");
        int currentPage = 1;
        int recordsPerPage = 5;

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.LIST_CLASS + "?id=" + section.getId());

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");


        Mockito.doReturn(initialClasses).when(classDAO).listClassesById(0, 5, section.getId());
        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.LIST_CLASS_PAGE))).thenReturn(requestDispatcher);
        Mockito.when(mockRequest.getParameter(eq("currentPage"))).thenReturn(Integer.toString(currentPage));
        Mockito.when(mockRequest.getParameter(eq("recordsPerPage"))).thenReturn(Integer.toString(recordsPerPage));
        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(section.getId()));

        classServlet.init();
        classServlet.setClassDAO(classDAO);
        classServlet.doPost(mockRequest, mockResponse);

        assertEquals(neededClasses, initialClasses);
    }
}