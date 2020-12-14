import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class TestServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title> TestServlet </title>");
		out.println("</head>");
		out.println("<body>");
		out.println("Hello from Servlet!");
		out.println("</body>");
		out.println("</html>");
	}
}