import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*;
import java.util.*;

public class WelcomeServlet4 extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException
	{
		response.setContentType("text/html"); 
		PrintWriter out = response.getWriter(); 
		out.println("<HTML>"); out.println("<HEAD>"); 
		out.println("<TITLE>Welcome</TITLE>");
		out.println("</HEAD>"); 
		out.println("<BODY>");
		out.println("<P>Welcome to Vik's site.</P>");
		out.println("</BODY>");
		out.println("</HTML>");
		Enumeration enumeration = request.getParameterNames(); 

		while (enumeration.hasMoreElements()) {
			String parameterName = (String)enumeration.nextElement();
			out.println(parameterName+": "+request.getParameter(parameterName)+"<BR>");
		}
	}
}
