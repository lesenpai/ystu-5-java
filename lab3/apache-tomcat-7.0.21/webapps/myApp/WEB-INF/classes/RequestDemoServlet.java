import java.io.IOException;
import javax.servlet.*;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import static java.lang.System.out;

public class RequestDemoServlet extends HttpServlet {

    ServletConfig config;

    @Override
    public void init(ServletConfig config) throws ServletException {
        
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException
    {
        out.printf("[Server Port: %s]\n", request.getServerPort());
        out.printf("[Server Name: %s]\n", request.getServerName());
        out.printf("[Protocol: %s]\n", request.getProtocol());
        out.printf("[Character Encoding: %s]\n", request.getCharacterEncoding());

        out.printf("[Content Type: %s]\n", request.getContentType());
        out.printf("[Content Length: %s]\n", request.getContentLength());
        out.printf("[Remote Address: %s]\n", request.getRemoteAddr());
        out.printf("[Remote Host: %s]\n", request.getRemoteHost());
        out.printf("[Scheme: %s]\n", request.getScheme());

        Enumeration parameters = request.getParameterNames();
        
        while (parameters.hasMoreElements()) {
            String parameterName = (String) parameters.nextElement();
            out.printf("[Parameter Name: %s]\n", parameterName);
            out.printf("[Parameter Value: %s]\n", request.getParameter(parameterName));
        }
        
        Enumeration attributes = request.getAttributeNames();
        
        while (attributes.hasMoreElements()) {
            String attribute = attributes.nextElement().toString();
            out.printf("[Attribute name: %s]\n", attribute);
            out.printf("[Attribute value: %s]\n", request.getAttribute(attribute));
        }
    }
}
