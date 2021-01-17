import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet(value="/cookie")
public class ViewCookieServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		Cookie cookie;
		PrintWriter out = response.getWriter();
		out.println("<HTML>");
		out.println("<HEAD>");
		out.println("<TITLE>View cookies</TITLE>");
		out.println("</HEAD>");
		out.println("<BODY>");
		out. println("<BR><H2>View cookies</H2>");
		for (Cookie value : cookies) {
			cookie = value;
			out.println("<B>Cookie Name:</B> " + cookie.getName() + "<BR>");
			out.println("<B>Cookie Value:</B> " + cookie.getValue() + "<BR>");
		}
		out.println("</BODY>");
		out.println("</HTML>");
		out.println("<A HREF=login>Go back</A> to the Login Page");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
