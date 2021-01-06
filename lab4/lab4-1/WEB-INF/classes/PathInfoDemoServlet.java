import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class PathInfoDemoServlet extends HttpServlet {
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
		out.println("getPathInfo: "+request.getPathInfo()+"<br>");
		out.println("getPathTranslated: "+request.getPathTranslated()+"<br>"); 
		out.println("</body>");
		out.println("</html>");
	}
}
