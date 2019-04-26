package commands.login;

import constants.Blanks;
import constants.URL;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import servlets.LoginServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;

public class GetLoginPageCommandTest {
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
        LoginServlet lginServlet = new LoginServlet() {
            public ServletContext getServletContext() {
                return servletContext;
            }
        };

        RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);

        Mockito.when(mockRequest.getRequestDispatcher(eq(Blanks.LOGIN_PAGE))).thenReturn(requestDispatcher);

        lginServlet.init();
        lginServlet.doGet(mockRequest, mockResponse);
    }
}