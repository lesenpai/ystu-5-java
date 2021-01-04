import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*;
import java.util.*;

public class WelcomeServlet5 extends HttpServlet {
 public void doGet(HttpServletRequest request, 
HttpServletResponse response) 
throws ServletException, IOException
{
 response.setContentType("text/html"); 
PrintWriter out = response.getWriter(); 
out.println("<HTML>"); out.println("<HEAD>"); 
out.println("<TITLE>Welcome</TITLE>");
 out.println("</HEAD>"); 
out.println("<BODY>");
out.println("<P>Welcome to the Bulbul's and Boni's Web Site.</P>");
out.println("Query String: " + request.getQueryString() + "<BR>"); 
out.println("</BODY>");
out.println("</HTML>");
}
}
