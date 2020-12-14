import javax.servlet.*; 
import java.io.IOException;
import java.io.PrintWriter;

public class SimpleServlet extends GenericServlet {

	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {

		PrintWriter w = response.getWriter();
		w.println("<HTML>");
		w.println("<HEAD>");
		w.println("<TITLE>");
		w.println("Simple servlet");
		w.println("</TITLE>");
		w.println("</HEAD>");
		w.println("<BODY>");
		w.println("<h1>Extending GenericServlet makes your life better.</h1>");
		w.println("</BODY>");
		w.println("</HTML>");
	}
}
