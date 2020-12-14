import java.io.IOException;
import javax.servlet.*;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.lang.System.out;

public class AttributeSetterServlet extends HttpServlet {

    ServletConfig config;

    @Override
    public void init(ServletConfig config)
            throws ServletException 
    {
        this.config = config;
        ServletContext servletContext = this.config.getServletContext();
        servletContext.setAttribute("password", "dingdong");
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
        out.println("<<AttributeSetterServlet>>");
    }

    @Override
    public void destroy() {

    }

    public String getServletlnfo() {
        return null;
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }
}
