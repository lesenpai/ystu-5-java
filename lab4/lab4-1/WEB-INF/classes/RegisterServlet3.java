import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class RegisterServlet3 extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>The GET method</title>");
		out.println("</head>"); 
		out.println("<body>");
		out.println("The servlet has received a GET. Now, click the button below."); 
		out.println("<br>");
		out.println("Query String: "+request.getQueryString()+"<br>"); 
		out.println("<form method=GET>");
		out.println("<br>First Name: <input type=text name=FirstName>");
		out.println("<br>Last Name: <input type=text name=LastName>");
		out.println("<br><input type=submit value=Submit>");
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
}
