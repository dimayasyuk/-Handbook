package commands.registration;

import constants.Blanks;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import servlets.RegisterServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

public class GetRegistrationFormCommandTest {
    private ServletContext servletContext;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;

    @Before
    public void setUp() throws Exception {
        servletContext = Mockito.mock(ServletContext.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void execute() throws ServletException, IOException {
        RegisterServlet registerServlet = new RegisterServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.REGISTRATION_FORM))).thenReturn(requestDispatcher);

        registerServlet.init();
        registerServlet.doGet(mockRequest, mockResponse);
    }
}