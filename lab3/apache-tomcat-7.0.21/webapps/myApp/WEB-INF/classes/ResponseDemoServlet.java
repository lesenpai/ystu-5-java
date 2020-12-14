import javax.servlet.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Enumeration;
import javax.servlet.http.HttpServlet;

public class ResponseDemoServlet extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {}
    
    public void destroy() {}
    
    public void service(ServletRequest request, ServletResponse response)
        throws ServletException, IOException 
    {
        PrintWriter w = response.getWriter();
        w.println("<HTML>");
        w.println("<HEAD>");
        w.println("<TITLE>");
        w.println("ServletResponse");
        w.println("</TITLE>");
        w.println("</HEAD>");
        w.println("<BODY>");
        w.println("<B>Demonstrating the ServletResponse object</B>");
        w.println("<BR>");
        w.println("<BR>Server Port: " + request.getServerPort());
        w.println("<BR>Server Name: " + request.getServerName()); 
        w.println("<BR>Protocol: " + request.getProtocol()); 
        w.println("<BR>Characte Encoding: " +request.getCharacterEncoding()); 
        w.println("<BR>Content Type: " + request.getContentType()); 
        w.println("<BR>Content Length: " + request.getContentLength()); 
        w.println("<BR>Remote Address: " + request.getRemoteAddr()); 
        w.println("<BR>Remote Host: " + request.getRemoteHost()); 
        w.println("<BR>Scheme: " + request.getScheme()); 
        Enumeration parameters = request.getParameterNames();
        while (parameters.hasMoreElements()) {
            String parameterName = parameters.nextElement().toString();
            w.println("<br>Parameter Name: " + parameterName);
            w.println("<br>Parameter Value: " +
                request.getParameter(parameterName));
        }
        Enumeration attributes = request.getAttributeNames();
        while (attributes.hasMoreElements()) {
            String attribute = (String) attributes.nextElement();

            w.println("<BR>Attribute name: " + attribute);
            w.println("<BR>Attribute value: " + request.getAttribute(attribute));
        }
        w.println("</BODY>"); 
        w.println("</HTML>");
    }

    public String getServletlnfo() {
        return null;
    }

    public ServletConfig getServletConfig() {
        return null;
    }
}