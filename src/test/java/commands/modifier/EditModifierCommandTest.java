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
import java.sql.Date;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;

public class EditModifierCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ModifierDAO modifierDAO;
    private Modifier neededModifier;
    private Modifier editModifier;


    @Before
    public void init(){
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        modifierDAO = Mockito.mock(ModifierDAO.class);
        Mockito.doAnswer((args) -> {
            neededModifier = args.getArgument(1);
            return null;
        }).when(mockRequest).setAttribute(Mockito.any(), Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
        Date date = new Date(System.currentTimeMillis());
        editModifier = new Modifier(1, "private", "The type or member can be accessed only by code in the same class or struct.",
                date, date);
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

        Mockito.when(mockRequest.getRequestURI()).thenReturn(URL.EDIT_MODIFIER);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(editModifier.getId()));
        Mockito.doReturn(editModifier).when(modifierDAO).getModifier(editModifier.getId());
        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.NEW_MODIFIER_PAGE))).thenReturn(requestDispatcher);

        modifierServlet.init();
        modifierServlet.setModifierDAO(modifierDAO);
        modifierServlet.doGet(mockRequest, mockResponse);

        assertEquals(editModifier, neededModifier);
    }
}