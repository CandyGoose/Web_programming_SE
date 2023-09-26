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

@WebServlet(name = "ControllerServlet", value = "", loadOnStartup = 0)
@MultipartConfig
public class ControllerServlet extends HttpServlet
{
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log(String.format("Receiving \"%s\" request from %s", req.getParameter("function"), req.getSession().getId()));
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
