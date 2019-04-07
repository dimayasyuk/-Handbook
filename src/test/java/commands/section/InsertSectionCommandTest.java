package commands.section;

import constants.URL;
import dao.SectionDAO;
import entities.Section;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import servlets.SectionServlet;
import constants.Blanks;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;

public class InsertSectionCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private SectionDAO sectionDAO;
    private Section neededSection;
    private Section insertSection;


    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        sectionDAO = Mockito.mock(SectionDAO.class);
        Mockito.doAnswer((args) -> {
            neededSection = args.getArgument(0);
            return null;
        }).when(sectionDAO).insertSection(Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
        insertSection = new Section(1, "key words", "The type or member can be accessed only by code in the same class or struct.");
    }

    @Test
    public void execute() throws ServletException, IOException {
        SectionServlet modifierServlet = new SectionServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.INSERT_SECTION);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getParameter(eq("name"))).thenReturn(insertSection.getName());
        Mockito.when(mockRequest.getParameter(eq("description"))).thenReturn(insertSection.getDescription());

        modifierServlet.init();
        modifierServlet.setSectionDAO(sectionDAO);
        modifierServlet.doGet(mockRequest, mockResponse);

        assertTrue(neededSection.getDescription() == insertSection.getDescription());
        assertTrue(neededSection.getName() == insertSection.getName());
    }
}