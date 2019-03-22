package commands.modifier;

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
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;

public class UpdateModifierCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ModifierDAO modifierDAO;
    private Modifier neededModifier;
    private List<Modifier> initialModifiers;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        modifierDAO = Mockito.mock(ModifierDAO.class);
        Mockito.doAnswer((args) -> {
            neededModifier = args.getArgument(0);
            return null;
        }).when(modifierDAO).updateModifier(Mockito.any());
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

        Mockito.when(mockRequest.getRequestURI()).thenReturn(URL.UPDATE_MODIFIER);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");
        Date date = new Date(System.currentTimeMillis());
        Modifier newModifier = new Modifier(1, "public", "The type or member can be accessed only by code in the same class or struct.",
                date, date);

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(newModifier.getId()));
        Mockito.when(mockRequest.getParameter(eq("name"))).thenReturn(newModifier.getName());
        Mockito.when(mockRequest.getParameter(eq("description"))).thenReturn(newModifier.getDescription());
        Mockito.when(mockRequest.getParameter(eq("created"))).thenReturn(newModifier.getCreated().toString());

        modifierServlet.init();
        modifierServlet.setModifierDAO(modifierDAO);
        modifierServlet.doGet(mockRequest, mockResponse);

        Optional<Modifier> matchingModifier = initialModifiers.stream().
                filter(modifier -> modifier.getId() == newModifier.getId()).
                findFirst();

        assertFalse(neededModifier.getName() == matchingModifier.get().getName());
        assertTrue(neededModifier.getDescription() == matchingModifier.get().getDescription());
    }
}