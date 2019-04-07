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
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;

public class UpdateSectionCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private SectionDAO sectionDAO;
    private Section neededSection;
    private List<Section> initialSection;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        sectionDAO = Mockito.mock(SectionDAO.class);
        Mockito.doAnswer((args) -> {
            neededSection = args.getArgument(0);
            return null;
        }).when(sectionDAO).updateSection(Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
        initialSection = ModifierService.initSections();
    }

    @Test
    public void execute() throws IOException, SQLException, ServletException {
        SectionServlet modifierServlet = new SectionServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.UPDATE_SECTION);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");
        Date date = new Date(System.currentTimeMillis());
        Section newSection = new Section(1, "key words", "The type or member can be accessed only by code in the same class or struct.");

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(newSection.getId()));
        Mockito.when(mockRequest.getParameter(eq("name"))).thenReturn(newSection.getName());
        Mockito.when(mockRequest.getParameter(eq("description"))).thenReturn(newSection.getDescription());

        modifierServlet.init();
        modifierServlet.setSectionDAO(sectionDAO);
        modifierServlet.doGet(mockRequest, mockResponse);

        Optional<Section> matchingModifier = initialSection.stream().
                filter(modifier -> modifier.getId() == newSection.getId()).
                findFirst();

        assertFalse(neededSection.getName() == matchingModifier.get().getName());
        assertTrue(neededSection.getDescription() == matchingModifier.get().getDescription());
    }
}