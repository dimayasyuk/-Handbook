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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

public class EditSectionCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private SectionDAO sectionDAO;
    private Section neededSection;
    private Section editSection;


    @Before
    public void init(){
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        sectionDAO = Mockito.mock(SectionDAO.class);
        Mockito.doAnswer((args) -> {
            neededSection = args.getArgument(1);
            return null;
        }).when(mockRequest).setAttribute(Mockito.any(), Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
        editSection = new Section(1, "key words", "The type or member can be accessed only by code in the same class or struct.");
    }

    @Test
    public void execute() throws IOException, SQLException, ServletException {
        SectionServlet modifierServlet = new SectionServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(mockRequest.getRequestURI()).thenReturn(Blanks.BASE_URL + URL.EDIT_SECTION);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(editSection.getId()));
        Mockito.doReturn(editSection).when(sectionDAO).getSection(editSection.getId());
        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.NEW_SECTION_PAGE))).thenReturn(requestDispatcher);

        modifierServlet.init();
        modifierServlet.setSectionDAO(sectionDAO);
        modifierServlet.doGet(mockRequest, mockResponse);

        assertEquals(editSection, neededSection);
    }
}