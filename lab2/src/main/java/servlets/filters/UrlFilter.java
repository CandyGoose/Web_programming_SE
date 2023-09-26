package servlets.filters;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebFilter(filterName = "UrlFilter")
public class UrlFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest req,
                         HttpServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {
        if (req.getRequestURI().matches(".*(css|ico|js|woff)")) {
            chain.doFilter(req, resp);
            return;
        }

        if (!Objects.equals(req.getHttpServletMapping().getServletName(), "ControllerServlet")) {
            if(req.getHttpServletMapping().getMatchValue().isEmpty()) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            } else {
                resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            }
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
            return;
        }

        try {
            chain.doFilter(req, resp);
        } catch (ServletException | IOException e) {
            req.getServletContext().log("Caught exception", e);
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            req.getRequestDispatcher("/jsp/error.jsp").forward(req, resp);
        }
    }
}