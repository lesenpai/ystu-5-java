import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value="/action")
public class ActionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Action</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>Action page</h1>");
		out.println("<ul style='list-style-type:none;'>");
		out.println("<li><a href=%s>View teams</a></li>".formatted(MyConst.UrlRoot+"/view-team"));
		out.println("<li><a href=%s>View players</a></li>".formatted(MyConst.UrlRoot+"/view-player"));
		out.println("</ul>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}
}
