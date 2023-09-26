package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * The ControllerServlet class is responsible for handling HTTP GET and POST requests and
 * routing them to appropriate parts of the web application based on the "function" parameter.
 */
@WebServlet(name = "ControllerServlet", value = "", loadOnStartup = 0)
@MultipartConfig
public class ControllerServlet extends HttpServlet
{
    /**
     * Handles HTTP POST requests. If the "function" parameter is provided and equals "clear," it clears a collection
     * stored in the servlet context, logs the action, and forwards the request to "/jsp/table.jsp."
     *
     * @param req  The HttpServletRequest object representing the request.
     * @param resp The HttpServletResponse object representing the response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getParameter("function"))) {
            log(String.format("Receiving \"%s\" request from %s", req.getParameter("function"), req.getSession().getId()));
            if (req.getParameter("function").equals("clear")) {
                ((Collection<?>) getServletContext().getAttribute("results")).clear();
                log("Cleared table!");
                req.getRequestDispatcher("/jsp/table.jsp").forward(req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            }
        } else
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }

    /**
     * Handles HTTP GET requests. If the "function" parameter is provided and equals "check," it forwards the request.
     *
     * @param req  The HttpServletRequest object representing the request.
     * @param resp The HttpServletResponse object representing the response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Objects.nonNull(req.getParameter("function"))) {
            log(String.format("Receiving \"%s\" request from %s", req.getParameter("function"), req.getSession().getId()));
            if (req.getParameter("function").equals("check")) {
                req.getRequestDispatcher("/check").forward(req, resp);
            } else {
                resp.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
            }
        } else {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
