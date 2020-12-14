package servlet;

import java.io.IOException;
import javax.servlet.*;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfigDemoServlet extends HttpServlet {

    ServletConfig config;

    @Override
    public void init(ServletConfig config)
            throws ServletException {
        System.out.println("[init]");
        this.config = config;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        System.out.println("[service]");
        Enumeration parameters = config.getInitParameterNames();
        while (parameters.hasMoreElements()) {
            String parameter = (String) parameters.nextElement();
            System.out.printf("[Parameter name: %s]\n", parameter);
            System.out.printf("[Parameter value: %s]\n", config.getInitParameter(parameter));
        }
    }

    @Override
    public void destroy() {
        System.out.println("[destroy]");
    }

    public String getServletlnfo() {
        return null;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
}
