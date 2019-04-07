package commands.section;

import commands.classes.ClassService;
import constants.Blanks;
import constants.URL;
import dao.ClassDAO;
import dao.ContentDAO;
import dao.SectionDAO;
import entities.Class;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import servlets.SectionServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;

public class NewSectionCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void execute() throws IOException, SQLException, ServletException {
        SectionServlet sectionServlet = new SectionServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.NEW_SECTION);

        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.NEW_SECTION_PAGE))).thenReturn(requestDispatcher);

        sectionServlet.init();
        sectionServlet.doGet(mockRequest, mockResponse);
    }

}