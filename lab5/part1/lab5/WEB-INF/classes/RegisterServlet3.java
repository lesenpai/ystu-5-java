import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class RegisterServlet3 extends HttpServlet {
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
out.println("<FORM METHOD=GET>");
out.println("<BR>First Name: <INPUT TYPE=TEXT NAME=FirstName>");
out.println("<BR>Last Name: <INPUT TYPE=TEXT NAME=LastName>");
out.println("<BR><INPUT TYPE=SUBMIT VALUE=Submit>");
out.println("</FORM>");
 out.println("</BODY>");
 out.println("<HTML>");
}
}