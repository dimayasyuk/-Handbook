package commands.classes;

import constants.Blanks;
import constants.URL;
import dao.ClassDAO;
import dao.ContentDAO;
import dao.SectionDAO;
import entities.Class;
import entities.Content;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

public class EditClassCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ClassDAO classDAO;
    private Object neededClass;
    private SectionDAO sectionDAO;
    private ContentDAO contentDAO;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        classDAO = Mockito.mock(ClassDAO.class);
        sectionDAO = Mockito.mock(SectionDAO.class);
        contentDAO = Mockito.mock(ContentDAO.class);
        Mockito.doAnswer((args) -> {
            if (args.getArgument(1) instanceof Class) {
                neededClass = args.getArgument(1);
            }
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

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.EDIT_CLASS);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Date date = new Date(System.currentTimeMillis());
        Class insertClass = new Class(1, "Command", "command", date, date, 1);
        Content content = new Content(1, "String");

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(insertClass.getId()));
        Mockito.doReturn(insertClass).when(classDAO).getClassById(insertClass.getId());
        Mockito.doReturn(content).when(contentDAO).getContentById(content.getId());

        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.NEW_CLASS_PAGE))).thenReturn(requestDispatcher);

        classServlet.init();
        classServlet.setDAO(classDAO, sectionDAO);
        classServlet.setContentDAO(contentDAO);
        classServlet.doGet(mockRequest, mockResponse);

        assertEquals(neededClass, insertClass);
    }
}