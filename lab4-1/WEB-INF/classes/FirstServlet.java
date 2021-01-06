import javax.servlet.*;
import javax.servlet.http.*; 
import java.io.*;
public class FirstServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException { 
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter(); 
		out.println("<html>"); 
		out.println("<head>");
		out.println("<title>Included Request Parameters</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<b>Included Request Parameters</b><br>"); 
		RequestDispatcher rd = request.getRequestDispatcher("/s2?name=le"); 
		rd.include(request, response); 
		out.println("</body>"); 
		out.println("</html>");
	}
}
