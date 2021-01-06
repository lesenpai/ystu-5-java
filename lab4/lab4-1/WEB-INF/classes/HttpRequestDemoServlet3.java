import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*; 
import java.util.*;

public class HttpRequestDemoServlet3 extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter(); 
		out.println("<html>"); 
		out.println("<head>");
		out.println("<title>Obtaining Multi-Value Parameters</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("Select your favorite music:"); 
		out.println("<br><form method=POST>"); 
		out.println("<br><input type=checkbox name=favoriteMusic value=Rock>Rock"); 
		out.println("<br><input type=checkbox name=favoriteMusic value=Jazz>Jazz"); 
		out.println("<br><input type=checkbox name=favoriteMusic value=Classical>Classical"); 
		out.println("<br><input type=checkbox name=favoriteMusic value=Country>Country"); 
		out.println("<br>"); 
		out.println("<br><input type=submit value=Submit>");
		out.println("</form>"); 
		out.println("</body>"); 
		out.println("</html>");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		String[] values = request.getParameterValues("favoriteMusic");
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter(); 
		if (values != null ) {
			int length = values.length;
			out.println("You have selected: ");
			out.println("<ul>");
			for (int i=0; i<length; i++) { 
				out.println("<li>"+values[i]+"</li>");
			}
			out.println("</ul>");
		}
	}
}
