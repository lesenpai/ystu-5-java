import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*; 

public class HttpRequestDemoServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter(); 
		out.println("<html>"); 
		out.println("<head>");
		out.println("<title>Obtaining the Query String</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("Query String: "+request.getQueryString()+"<br>"); 
		out.println("<form method=GET>");
		out.println("<br>First Name: <input type=text name=FirstName>");
		out.println("<br>Last Name: <input type=text name=LastName>");
		out.println("<br><INPUT type=submit value=Submit>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
}
