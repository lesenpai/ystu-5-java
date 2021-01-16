package lab5.part2;

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
		out.println("<h1>Action Page</h1>");
		out.println("<ul style='list-style-type:none;'>");
		out.println("<li><a href=%s>Manufacturers View</a></li>".formatted(MyConst.UrlRoot+"manufacturer"));
		out.println("<li><a href=%s>Products View</a></li>".formatted(MyConst.UrlRoot+"product"));
		out.println("</ul>");
		out.println("</body>");
		out.println("</html>");
	}
}
