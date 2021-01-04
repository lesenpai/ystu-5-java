import javax.servlet.*;
import javax.servlet.http.*; 
import java.io.*;
public class FirstServlet1 extends HttpServlet {
public void doGet(HttpServletRequest request, 
HttpServletResponse response) 
throws ServletException, IOException { 
response.setContentType("text/html"); 
PrintWriter out = response.getWriter(); 
out.println("<HTML>"); 
out.println("<HEAD>");
out.println("<TITLE>Included HTML</TITLE>");
out.println("</HEAD>");
out.println("<BODY>");
out.println("<B>Included HTML</B><BR>"); 
RequestDispatcher rd = request.getRequestDispatcher("/soobchenie.html");
rd.include(request, response);
out.println("</B0DY>"); 
out.println("</HTML>");
}
}
