package servlets;

import data.Coordinates;
import data.Result;
import data.ResultContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;


@WebServlet(name = "AreaCheckServlet", value = "/check")
public class AreaCheckServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long startTime = System.nanoTime();
        ResultContainer resultList = (ResultContainer) req.getServletContext().getAttribute("results");

        Coordinates coordinates = null;
        Result.Type result = Result.Type.NONE;
        try {
            int r = Integer.parseInt(req.getParameter("r"));
            BigDecimal x = new BigDecimal(req.getParameter("x"));
            BigDecimal y = new BigDecimal(req.getParameter("y"));
            boolean dot = Boolean.parseBoolean(req.getParameter("dot"));

            log(String.format("Check dot (%s,%s,%s,%s)", x, y, r, dot));
            coordinates = new Coordinates(x, y, r);
            if (validate(x, y, r, dot)) {
                boolean inArea = inArea(x.doubleValue(), y.doubleValue(), r);
                result = inArea ? Result.Type.HIT : Result.Type.FAIL;
            } else {
                result = Result.Type.FAILED_VALIDATING;
            }
        } catch (NumberFormatException e) {
            result = Result.Type.WRONG_FORMAT;
        } finally {
            resultList.add(new Result(
                    coordinates,
                    new Date(),
                    (double) (System.nanoTime() - startTime) / 1000000,
                    result
            ));
            req.getRequestDispatcher("/jsp/table.jsp").forward(req, resp);
        }
    }

    public static final Set<Integer> availableX = new HashSet<>(Arrays.asList(-5, -4, -3, -2, -1, 0, 1, 2, 3));
    public static final Set<Integer> availableR = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));

    public static boolean validate(BigDecimal x, BigDecimal y, int r, boolean dot) {
        boolean isXValid = availableX.contains(x.intValue());
        boolean isYValid = y.compareTo(new BigDecimal("-3")) > 0 && y.compareTo(new BigDecimal("5")) < 0;
        return (dot && availableR.contains(r)
                || isXValid
                && isYValid
                && availableR.contains(r));
    }

    public static double batman_upper(double x) {
        x = Math.abs(x);
        if (x < 0.5) {
            return 2.25;
        } else if (0.5 <= x && x < 0.75) {
            return 3 * x + 0.75;
        } else if (0.75 <= x && x < 1.0) {
            return 9 - 8 * x;
        } else if (1 <= x && x < 3) {
            return (1.5 - 0.5 * x - 3 * Math.sqrt(10) / 7 * (Math.sqrt(3 - x * x + 2 * x) - 2));
        } else if (3 <= x && x <= 7) {
            return 3 * Math.sqrt(-(x / 7) * (x / 7) + 1);
        }
        return 0.0;
    }

    public static double batman_lower(double x) {
        x = Math.abs(x);
        if (0 <= x && x < 4) {
            return (Math.abs(x / 2) - (3 * Math.sqrt(33) - 7) / 112 * x * x +
                    Math.sqrt(1 - (Math.abs(x - 2) - 1) * (Math.abs(x - 2) - 1)) - 3);
        } else if (4 <= x && x <= 7) {
            return -3 * Math.sqrt(-(x / 7) * (x / 7) + 1);
        }
        return 0.0;
    }

    public static boolean inArea(double x, double y, double r) {
        double yUpper = batman_upper(x * 7 / r);
        double yLower = batman_lower(x * 7 / r);
        return y * 7 / r >= yLower && y * 7 / r <= yUpper;
    }
}
