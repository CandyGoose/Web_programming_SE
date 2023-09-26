package servlets.filters;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * The UrlFilter class is a servlet filter responsible for filtering incoming HTTP requests based on their URLs and
 * servlet mappings. It performs URL-based filtering to handle static resources and control access to specific
 * servlets.
 */
@WebFilter(filterName = "UrlFilter")
public class UrlFilter extends HttpFilter {
    /**
     * Filters incoming HTTP requests based on their URLs and servlet mappings. It allows access to resources
     * (e.g., CSS, JavaScript, JSP files) and controls access to specific servlets.
     *
     * @param req    The HttpServletRequest object representing the request.
     * @param resp   The HttpServletResponse object representing the response.
     * @param chain  The FilterChain object for executing the next filter in the chain.
     * @throws IOException      If an I/O error occurs.
     * @throws ServletException If a servlet-specific error occurs.
     */
    @Override
    public void doFilter(HttpServletRequest req,
                         HttpServletResponse resp,
                         FilterChain chain) throws IOException, ServletException {

        // Allow access to static resources (e.g., CSS, JavaScript, JSP files)
        if (req.getRequestURI().matches(".*(css|ico|js|woff|jsp)")) {
            chain.doFilter(req, resp);
            return;
        }

        // Control access to specific servlets
        if (!Objects.equals(req.getHttpServletMapping().getServletName(), "ControllerServlet")) {
            if (req.getHttpServletMapping().getMatchValue().isEmpty()) {
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