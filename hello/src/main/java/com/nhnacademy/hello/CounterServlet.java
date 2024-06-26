package com.nhnacademy.hello;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.logging.Logger;


@WebServlet(
        name ="counterServlet",
        urlPatterns = "/counter",
        initParams = {
                @WebInitParam(
                        name = "counter",
                        value = "100"
                )
        }
)
public class CounterServlet extends HttpServlet {
    private static final Logger log = Logger.getLogger(CounterServlet.class.getName());

    private long counter;

    @Override
    public void init() throws ServletException {
        counter = Optional.ofNullable(getInitParameter("counter"))
                .map(Long::parseLong)
                .orElse(0l);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        counter++;
        CounterUtils.increaseCounter(getServletContext());
        try (PrintWriter writer = resp.getWriter()) {
            writer.println("<!DOCTYPE html>");
            writer.println("<html>");
            writer.println("<head>");
            writer.println("<meta charset='utf-8'>");
            writer.println("</head>");
            writer.println("<body>");
            writer.printf("<h1>%d</h1>\n", counter);
            writer.println("<h1> counterCheck : "+getServletContext().getAttribute("counterCheck") + "</h1>");
            writer.println("</body>");
            writer.println("</html>");
        } catch (IOException ex) {
            log.info(ex.getMessage());
        }
    }
}