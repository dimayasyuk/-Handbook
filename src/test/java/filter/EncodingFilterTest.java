package filter;

import constants.URL;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.eq;

public class EncodingFilterTest {
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;
    private FilterChain filterUnderTest;

    @Before
    public void setUp() {
        filterUnderTest = Mockito.mock(FilterChain.class);
        mockRequest = Mockito.mock(HttpServletRequest.class);
        mockResponse = Mockito.mock(HttpServletResponse.class);
    }

    @Test
    public void doFilter() throws IOException, ServletException {
        Mockito.when(mockRequest.getServletPath()).thenReturn(URL.LIST_SECTION);
        EncodingFilter filter = new EncodingFilter();

        filter.init(null);
        filter.doFilter(mockRequest, mockResponse, filterUnderTest);
        filter.destroy();

        Mockito.verify(mockRequest, Mockito.times(1)).setCharacterEncoding(eq("UTF-8"));
    }
}