import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*;

public class WelcomeServlet6 extends HttpServlet { 
	public void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter(); 
		out.println("<html>"); 
		out.println("<head>"); 
		out.println("<title>Welcome</title>"); 
		out.println("</head>"); 
		out.println("<body>");
		out.println("<p>Welcome to Lev's site.</p>");
		out.println("</body>");
		out.println("</html>");
	}
}
