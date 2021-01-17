import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet(value = "/check-cookie")
public class CheckCookieServlet extends HttpServlet {
	String nextUrl = "";

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response. getWriter();
		if (request.getParameter("flag")==null) {
			// первый запрос
			Cookie cookie = new Cookie("browserSetting", "on");
			response.addCookie(cookie);
			nextUrl = request.getRequestURI() + "?flag=1";

			out.println("<META HTTP-EQUIV=Refresh CONTENT=0;URL=" + nextUrl +">");
		}
		else {
			// второй запрос
			Cookie[] cookies = request.getCookies();
			if (cookies!=null) {
				boolean cookieFound = false;
				for (Cookie cookie : cookies) {
					if (cookie.getName().equals("browserSetting") && cookie.getValue().equals("on")) {
						cookieFound = true;
						break;
					}
				}
				if (cookieFound) {
					out.println("Your browser's cookie setting is on.");
				}
				else {
					out.println("Your browser does not support cookies or the cookie setting is off.");
				}
			}
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
