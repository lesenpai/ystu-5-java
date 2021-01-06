import javax.servlet.*;
import javax.servlet.http.*; 
import java.io.*; 
import java.util.*;

public class RegisterServlet2 extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		Enumeration enumeration = request.getHeaderNames();

		while (enumeration.hasMoreElements()) {
			String header = enumeration.nextElement().toString();
			out.println(header+": "+request.getHeader(header)+"<br>");
		}
	}
}
