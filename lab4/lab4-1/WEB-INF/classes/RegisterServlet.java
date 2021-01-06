import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class RegisterServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>The get method</title>");
		out.println("</head>"); 
		out.println("<body>");
		out.println("The servlet has received a get. Now, click the button below."); 
		out.println("<br>");
		out.println("<form method=POST>"); 
		out.println("<input type=submit value=Submit>"); 
		out.println("</form>");
		out.println("</body>");
		out.println("</html>");
	}
	public void doPost( HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter(); 
		out.println("<html>");
		out.println("<head>");
		out.println("<title>The POST method</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("The servlet has received a POST. Thank you.");
		out.println("</body>");
		out.println("</html>");
	}
}
