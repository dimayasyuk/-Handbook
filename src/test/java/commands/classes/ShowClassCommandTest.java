package commands.classes;

import constants.Blanks;
import constants.URL;
import dao.ClassDAO;
import dao.ContentDAO;
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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

public class ShowClassCommandTest {

    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ClassDAO classDAO;
    private ContentDAO contentDAO;
    private Class neededClass;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        classDAO = Mockito.mock(ClassDAO.class);
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
    public void execute() throws ServletException, IOException, SQLException {
        ClassServlet classServlet = new ClassServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };
        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
		
        Date date = new Date(System.currentTimeMillis());
        Class detailClass = new Class(1, "Command", "command", date, date, 1);
        Content content = new Content(1, "String");

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.SHOW_CLASS);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");


        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.DETAIL_CLASS_PAGE))).thenReturn(requestDispatcher);
		Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.LIST_CLASS_PAGE))).thenReturn(requestDispatcher);
        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(detailClass.getId()));
        Mockito.doReturn(detailClass).when(classDAO).getClassById(detailClass.getId());
        Mockito.doReturn(content).when(contentDAO).getContentById(content.getId());


        classServlet.init();
        classServlet.setClassDAO(classDAO);
        classServlet.setContentDAO(contentDAO);
        classServlet.doGet(mockRequest, mockResponse);

        assertEquals(detailClass, neededClass);
    }
}