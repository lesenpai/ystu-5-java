import java.io.IOException;
import javax.servlet.*;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.lang.System.out;

public class ContextDemoServlet extends HttpServlet {

    ServletConfig config;

    @Override
    public void init(ServletConfig config)
            throws ServletException 
    {
        this.config = config;
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException 
    {
        ServletContext servletContext = config.getServletContext();
        Enumeration attributes = servletContext.getAttributeNames();
        while (attributes.hasMoreElements()) {
            String attribute = (String) attributes.nextElement();
            out.printf("[Attribute name: %s]\n", attribute);
            out.printf("[Attribute value: %s]\n", servletContext.getAttribute(attribute));
        }
        out.printf("[Major version: %s]\n", servletContext.getMajorVersion());
        out.printf("[Minor version: %s]\n", servletContext.getMinorVersion());
        out.printf("[Server info: %s]\n", servletContext.getServerInfo());
    }
}
