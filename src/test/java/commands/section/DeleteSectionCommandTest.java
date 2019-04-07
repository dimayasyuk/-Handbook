package commands.section;

import constants.URL;
import constants.Blanks;
import dao.SectionDAO;
import entities.Section;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import servlets.SectionServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;

public class DeleteSectionCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private SectionDAO sectionDAO;
    private List<Section> neededSections;
    private List<Section> initialSections;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        sectionDAO = Mockito.mock(SectionDAO.class);
        Mockito.doAnswer((args) -> {
            Section deletedModifier = args.getArgument(0);
            neededSections = initialSections.stream()
                    .filter(e -> (e.getId() != deletedModifier.getId()))
                    .collect(Collectors.toList());
            return null;
        }).when(sectionDAO).deleteSection(Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void execute() throws ServletException, IOException {
        initialSections = new LinkedList<>();
        Section firstModifier = new Section(1, "key words",
                "The type or member can be accessed only by code in the same class or struct.");
        Section secondModifier = new Section(2, "operators",
                "The type or member can be accessed only by code in the same class, or in a class that is derived from that class.");
        initialSections.add(firstModifier);
        initialSections.add(secondModifier);

        SectionServlet modifierServlet = new SectionServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.DELETE_SECTION);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(firstModifier.getId()));

        modifierServlet.init();
        modifierServlet.setSectionDAO(sectionDAO);
        modifierServlet.doGet(mockRequest, mockResponse);

        assertEquals(neededSections.size(), initialSections.size() - 1);
        assertFalse(neededSections.contains(firstModifier));
    }
}