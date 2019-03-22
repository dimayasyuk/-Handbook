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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.eq;

public class DeleteModifierCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private ModifierDAO modifierDAO;
    private List<Modifier> neededModifiers;
    private List<Modifier> initialModifiers;

    @Before
    public void init() throws IOException, SQLException {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        modifierDAO = Mockito.mock(ModifierDAO.class);
        Mockito.doAnswer((args) -> {
            Modifier deletedModifier = args.getArgument(0);
            neededModifiers = initialModifiers.stream()
                    .filter(e -> (e.getId() != deletedModifier.getId()))
                    .collect(Collectors.toList());
            return null;
        }).when(modifierDAO).deleteModifier(Mockito.any());
        mockResponse = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void execute() throws ServletException, IOException {
        initialModifiers = new LinkedList<>();
        Date date = new Date(System.currentTimeMillis());
        Modifier firstModifier = new Modifier(1, "private",
                "The type or member can be accessed only by code in the same class or struct.", date,
                date);
        Modifier secondModifier = new Modifier(2, "protected",
                "The type or member can be accessed only by code in the same class, or in a class that is derived from that class.",
                date, date);
        initialModifiers.add(firstModifier);
        initialModifiers.add(secondModifier);

        ModifierServlet modifierServlet = new ModifierServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        Mockito.when(mockRequest.getRequestURI()).thenReturn(URL.DELETE_MODIFIER);

        Mockito.doReturn("jdbc:mysql://localhost:3306/vocabulary?serverTimezone=UTC").when(servletContext).getInitParameter("jdbcURL");
        Mockito.doReturn("root").when(servletContext).getInitParameter("jdbcUsername");
        Mockito.doReturn("12345").when(servletContext).getInitParameter("jdbcPassword");

        Mockito.when(mockRequest.getParameter(eq("id"))).thenReturn(Integer.toString(firstModifier.getId()));

        modifierServlet.init();
        modifierServlet.setModifierDAO(modifierDAO);
        modifierServlet.doGet(mockRequest, mockResponse);

        assertEquals(neededModifiers.size(), initialModifiers.size() - 1);
        assertFalse(neededModifiers.contains(firstModifier));
    }
}