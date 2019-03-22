package filter;

import constants.Blanks;
import constants.URL;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class VocabularyFilter implements Filter {
    private List<String> url;


    public void unitUrl() {
        url = new LinkedList<>();
        url.add(URL.START_FILTER);
        url.add(URL.MODIFIER_FILTER);
        url.add(URL.CLASS_FILTER);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        unitUrl();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        for (String path : url) {
            if (path.equals(req.getServletPath())) {
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }
        servletRequest.getServletContext().getRequestDispatcher(Blanks.NOT_FOUND_PAGE).forward(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
