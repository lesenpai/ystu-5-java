import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet(value = "/session")
public class CheckSessionServlet extends HttpServlet {
	public String loginUrl = MyConst.UrlRoot + "/login";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/ CheckSessionServlet");
		HttpSession session = request.getSession();
		if (session == null) {
			response.sendRedirect(loginUrl);
		}
		else {
			String loggedIn = (String) session.getAttribute("loggedIn");
			if (!loggedIn.equals("true")) {
				response.sendRedirect(loginUrl);
			}
			else {
				PrintWriter out = response.getWriter();
				out.println("<HTML>");
				out.println("<HEAD>");
				out.println("<TITLE>Welcome</TITLE>");
				out.println("</HEAD>");
				out.println("<BODY>");
				out.println("<BR><H2>View session</H2>");
				out.println("Okay!");
				out.println("<BR><A HREF=login>Go back</A>");
				out.println("</BODY>");
				out.println("</HTML>");
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
