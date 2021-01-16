import java.io.*;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value="/login")
public class LoginServlet extends HttpServlet {

	final String USERNAME_REGEX = "[a-zA-Z][a-zA-Z0-9_]{3,32}";

	@Override
	public void init() {
		SqlUtils.init();
	}

	private void sendLoginForm(HttpServletResponse response, String message) throws IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Login</title>");
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		if (message != null) {
			out.println("<p>%s</p>".formatted(message));
		}
		out.println("<br>");
		out.println("<br><h2>Login</h2>");
		out.println("<br>");
		out.println("<br>Please enter your username and password.");
		out.println("<br>");
		out.println("<br><form method=POST>");
		out.println("<table>");

		out.println("<tr>");
		out.println("<td>Username:</td>");
		out.println("<td><input type=text name=username></td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td>Password:</td>");
		out.println("<td><input type=password name=password></td>");
		out.println("</tr>");

		out.println("<tr>");
		out.println("<td><input type=submit name='login' value='Login'></td>");
		out.println("<td align=right><input type=submit name='createUser' value='Create user'></td>");
		out.println("</tr>");

		out.println("</table>");
		out.println("</form>");
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		sendLoginForm(response, null);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (request.getParameter("login") != null) {
			var username = request.getParameter("username");
			var password = request.getParameter("password");

			String message = null;
			try {
				// check if user exists -> go to action page
				if (SqlUtils.isExists("select 1 from User where username='%s' and password='%s'".formatted(username, password))) {
					response.sendRedirect(MyConst.UrlRoot+"action");
				}
				else {
					message = "Error! Wrong username/password";
				}
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
			sendLoginForm(response, message);
		}
		else if (request.getParameter("createUser") != null) {
			var username = request.getParameter("username");
			var password = request.getParameter("password");

			String message = null;
			try {
				// check if user already exists
				if (SqlUtils.isExists("select 1 from User where username='%s' and password='%s'".formatted(username, password))) {
					message = "Error! User '%s' already exists".formatted(username);
				}
				// user not exists -> try create user
				else {
					// check name to regex
					if (username.matches(USERNAME_REGEX)) {
						SqlUtils.execute("insert into User (username, password) values ('%s','%s')".formatted(username, password));
						message = "User '%s' added".formatted(username);
					}
					// bad username
					else {
						message = "Error! Bad username. Username must be 4-30 long and must start with a letter, can contain letters, numbers and '_'";
					}
				}
			}
			catch (SQLException ex) {
				ex.printStackTrace();
			}
			sendLoginForm(response, message);
		}
	}
}
