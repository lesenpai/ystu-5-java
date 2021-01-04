import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class PathInfoDemoServlet extends HttpServlet {
public void doGet(HttpServletRequest request, HttpServletResponse response) 
throws ServletException, IOException {
response.setContentType("text/html");
PrintWriter out = response.getWriter();
out.println("<HTML>");
out.println("<HEAD>");
out.println("<TITLE>The GET method</TITLE>");
out.println("</HEAD>"); 
out.println("<BODY>");
out.println("The servlet has received a GET. " +"Now, click the button below."); 
out.println("<BR>");
out.println("Query String: " + request.getQueryString() + "<BR>"); 
out.println("getPathInfo: " + request.getPathInfo() + "<BR>");
out.println("getPathTranslated: " + request.getPathTranslated() + "<BR>"); 
out.println("</BODY>");
out.println("</HTML>");
}
}
