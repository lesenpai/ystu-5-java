import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*; 
import java.util.*;

public class SecondServlet extends HttpServlet {
public void doGet(HttpServletRequest request, 
HttpServletResponse response) 
throws ServletException, IOException { 
response. setContentType("text/html"); 
PrintWriter out = response.getWriter(); 
Enumeration enumeration = request.getAttributeNames();
while (enumeration.hasMoreElements()) {
String attributeName = (String) enumeration.nextElement();
out.println(attributeName + ": " +
request.getAttribute(attributeName) + "<BR>");
}
}
}

