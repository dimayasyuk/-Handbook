package commands.classes;

import constants.URL;
import constants.Blanks;
import dao.ClassDAO;
import dao.ContentDAO;
import entities.Class;
import entities.Content;
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
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;

public class UpdateClassCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ClassDAO classDAO;
    private ContentDAO contentDAO;
    private Class neededClass;
    private List<Class> initialClasses;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        classDAO = Mockito.mock(ClassDAO.class);
        contentDAO = Mockito.mock(ContentDAO.class);
        Mockito.doAnswer((args) -> {
            if (args.getArgument(0) instanceof Class) {
                neededClass = args.getArgument(0);
            }
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

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.UPDATE_CLASS);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Date date = new Date(System.currentTimeMillis());
        Class newClass = new Class(1, "Bridge", "class", date, date, 1, 1);
        Content content = new Content(1, "String");

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(newClass.getId()));
        Mockito.when(mockRequest.getParameter(eq("name"))).thenReturn(newClass.getName());
        Mockito.when(mockRequest.getParameter(eq("description"))).thenReturn(newClass.getDescription());
        Mockito.when(mockRequest.getParameter(eq("created"))).thenReturn(newClass.getCreated().toString());
        Mockito.when(mockRequest.getParameter(eq("section"))).thenReturn(Integer.toString(newClass.getModifierId()));
        Mockito.when(mockRequest.getParameter(eq("content"))).thenReturn(content.getText());
        Mockito.when(mockRequest.getParameter(eq("contId"))).thenReturn(Integer.toString(newClass.getContentId()));
        Mockito.doReturn(true).when(contentDAO).updateContent(content);


        classServlet.init();
        classServlet.setClassDAO(classDAO);
        classServlet.setContentDAO(contentDAO);
        classServlet.doGet(mockRequest, mockResponse);

        Optional<Class> matchingClass = initialClasses.stream().
                filter(cls -> cls.getId() == newClass.getId()).
                findFirst();

        assertFalse(neededClass.getName() == matchingClass.get().getName());
        assertTrue(neededClass.getDescription() == matchingClass.get().getDescription());
    }
}