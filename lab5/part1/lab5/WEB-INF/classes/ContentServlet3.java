import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
public class ContentServlet3 extends HttpServlet { 
String soob;//для вывода сообщений
public String loginUrl = "LoginServSeans6"; 
/** Обработка запроса HTTP Get */
public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
Cookie[] cookies = request.getCookies(); 
int length = cookies.length; 
String userName = null;
String password = null;
Cookie cookie;
for (int i=0; i<length; i++) { 
cookie = cookies[i]; 
if (cookie.getName().equals("userName"))
userName = cookie.getValue(); 
else 
if (cookie.getName().equals("password"))
password = cookie.getValue();
}
PrintWriter out = response.getWriter();
out.println("<HTML>");
out.println("<HEAD>");
out.println("<TITLE>Vivod cookies</TITLE>");
out.println("</HEAD>");
out.println("<BODY>");
//out. println("Vivod cookies:");
out. println("<BR><H2>Vivod cookies</H2>");
for (int i=0; i<length; i++) 
{ 
cookie = cookies[i];
out.println("<B>Cookie Name:</B> " + cookie.getName() + "<BR>"); 
out.println("<B>Cookie Value:</B> " + cookie.getValue() + "<BR>");
}
out.println("</BODY>");
out.println("</HTML>");
out.println("<A HREF=LoginServSeans6>Go back</A> to the LoginServSeans6 Page");
}

/** Обработка запроса HTTP Post */
public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException { 
doGet(request, response);
}
}
