package commands.section;

import constants.Blanks;
import constants.URL;
import dao.SectionDAO;
import entities.Section;
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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

public class ListSectionCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private List<Section> neededSections;
    private List<Section> initialSections;

    @Before
    public void init() {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.doAnswer((args) -> {
            if (args.getArgument(1) instanceof List<?>) {
                neededSections = args.getArgument(1);
            }
            return null;
        }).when(mockRequest).setAttribute(Mockito.any(), Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
        initialSections = commands.section.ModifierService.initSections();
    }

    @Test
    public void execute() throws IOException, SQLException, ServletException {
        SectionServlet sectionServlet = new SectionServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };
        int currentPage = 1;
        int recordsPerPage = 5;

        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
        SectionDAO sectionDAO = Mockito.mock(SectionDAO.class);

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.LIST_SECTION);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.LIST_SECTION_PAGE))).thenReturn(requestDispatcher);
        Mockito.when(mockRequest.getParameter(eq("currentPage"))).thenReturn(Integer.toString(currentPage));
        Mockito.when(mockRequest.getParameter(eq("recordsPerPage"))).thenReturn(Integer.toString(recordsPerPage));
        Mockito.doReturn(initialSections).when(sectionDAO).listAllSections(0,10);
        Mockito.doReturn(1).when(sectionDAO).countSections();

        sectionServlet.init();
        sectionServlet.setSectionDAO(sectionDAO);
        sectionServlet.doGet(mockRequest, mockResponse);

        assertEquals(initialSections, initialSections);
    }
}