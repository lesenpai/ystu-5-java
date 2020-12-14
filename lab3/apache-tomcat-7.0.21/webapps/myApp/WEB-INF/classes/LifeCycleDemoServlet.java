package servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.lang.System.out;

public class LifeCycleDemoServlet extends HttpServlet {

    @Override
    public void init(ServletConfig config)
            throws ServletException {
        out.println("[init]");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        out.println("[service]");
    }

    @Override
    public void destroy() {
        out.println("[destroy]");
    }

    public String getServletlnfo() {
        return null;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
}
