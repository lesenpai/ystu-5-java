import javax.servlet.*; 
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleServlet extends GenericServlet {
    
    @Override
    public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
            
        PrintWriter w = response.getWriter();
        w.println("<HTML>");
        w.println("<HEAD>");
        w.println("<TITLE>");
        w.println("Part 2. Simple servlet");
        w.println("</TITLE>");
        w.println("</HEAD>");
        w.println("<BODY>");
        w.println("<h1>Simple servlet</h1>");
        w.println("</BODY>");
        w.println("</HTML>");
    }
}
