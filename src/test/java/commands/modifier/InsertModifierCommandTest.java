package commands.modifier;

import constants.URL;
import dao.ModifierDAO;
import entities.Modifier;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import servlets.ModifierServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.eq;

public class InsertModifierCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ModifierDAO modifierDAO;
    private Modifier neededModifier;
    private Modifier insertModifier;


    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        modifierDAO = Mockito.mock(ModifierDAO.class);
        Mockito.doAnswer((args) -> {
            neededModifier = args.getArgument(0);
            return null;
        }).when(modifierDAO).insertModifier(Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
        Date date = new Date(System.currentTimeMillis());
        insertModifier = new Modifier(1, "private", "The type or member can be accessed only by code in the same class or struct.",
                date, date);
    }

    @Test
    public void execute() throws ServletException, IOException {
        ModifierServlet modifierServlet = new ModifierServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        Mockito.when(mockRequest.getRequestURI()).thenReturn(URL.INSERT_MODIFIER);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getParameter(eq("name"))).thenReturn(insertModifier.getName());
        Mockito.when(mockRequest.getParameter(eq("description"))).thenReturn(insertModifier.getDescription());

        modifierServlet.init();
        modifierServlet.setModifierDAO(modifierDAO);
        modifierServlet.doGet(mockRequest, mockResponse);

        assertTrue(neededModifier.getDescription() == insertModifier.getDescription());
        assertTrue(neededModifier.getName() == insertModifier.getName());
    }
}