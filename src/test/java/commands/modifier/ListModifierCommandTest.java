package commands.modifier;

import constants.Blanks;
import constants.URL;
import dao.ModifierDAO;
import entities.Modifier;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import servlets.ModifierServlet;

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

public class ListModifierCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private List<Modifier> neededModifiers;
    private List<Modifier> initialModifiers;

    @Before
    public void init() {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        Mockito.doAnswer((args) -> {
            neededModifiers = args.getArgument(1);
            return null;
        }).when(mockRequest).setAttribute(Mockito.any(), Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
        initialModifiers = ModifierService.initModifiers();
    }

    @Test
    public void execute() throws IOException, SQLException, ServletException {
        ModifierServlet modifierServlet = new ModifierServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
        ModifierDAO modifierDAO = Mockito.mock(ModifierDAO.class);

        Mockito.when(mockRequest.getRequestURI()).thenReturn(URL.LIST_MODIFIER);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.LIST_MODIFIER_PAGE))).thenReturn(requestDispatcher);
        Mockito.doReturn(initialModifiers).when(modifierDAO).listAllModifiers();

        modifierServlet.init();
        modifierServlet.setModifierDAO(modifierDAO);
        modifierServlet.doGet(mockRequest, mockResponse);

        assertEquals(neededModifiers, initialModifiers);
    }
}