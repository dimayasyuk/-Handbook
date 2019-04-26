package filter;

import constants.Blanks;
import constants.Constants;
import constants.URL;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.nonNull;

public class VocabularyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;

        HttpSession session = req.getSession(true);

        if (nonNull(session.getAttribute("user")) && session.getAttribute("role").equals(Constants.ADMIN_ROLE)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            res.sendRedirect(Blanks.BASE_URL + URL.LOGIN);
        }
    }

    @Override
    public void destroy() {
    }
}
