import javax.servlet.*; 
import javax.servlet.http.*; 
import java.io.*; 
import java.util.*;
import java.sql.*;

public class LoginServSeans6 extends HttpServlet {
String persistedUserName=null;
static String soob;//Переменная для сохранения сообщений блоков catch
int i,id;
private Connection con = null; // соединение с БД
private Statement s = null; // оператор
private String sql=null;
// Загрузка драйвера JDBC
 public void init() { 
try {
Class.forName("com.mysql.jdbc.Driver").newInstance();
}
catch (ClassNotFoundException e) {   
soob=e.toString();
}
catch (Exception e) { 
soob=e.toString();
}
}
public void doGet(HttpServletRequest request, 
HttpServletResponse response) 
throws ServletException, IOException 
{ 
Cookie[] cookies = request.getCookies(); 
if (cookies != null) {
for (int i=0; i<cookies.length; i++) { 
Cookie cookie = cookies[i]; 
if (cookie.getName().equals("userName")) 
persistedUserName = cookie. getValue();
}}

sendLoginForm(response, false);
}
private void sendLoginForm(HttpServletResponse response, 
boolean withErrorMessage) throws ServletException, IOException {

response.setContentType("text/html");
PrintWriter out = response.getWriter();
out.println("<HTML>");
out.println("<HEAD>");
out.println("<TITLE>Login</TITLE>");
out.println("</HEAD>");
out.println("<BODY>");
out.println("<CENTER>");

if (withErrorMessage) out.println("Login failed. Please try again.<BR>");
out.println("<BR>");
out. println("<BR><H2>Login Page</H2>");
out.println("<BR>");
out.println("<BR>Please enter your user name and password.");
out.println("<BR>");
out.println("<BR><FORM METHOD=POST>");
out.println("<TABLE>");
out.println("<TR>");
out.println("<TD>User Name:</TD>");
out. println( "<TD><INPUT TYPE=TEXT NAME=userName></TD>");
out.println("</TR>");
out.println("<TR>");
out.println("<TD>Password:</TD>");
out.println("<TD><INPUT TYPE=PASSWORD NAME=password></TD>");
out.println("</TR>");
out.println("<TR>");
out.println("<BR>Please enter number:");
out.println("<BR>1 - Displaying All Book, 2 - Insert author, 3 - Displaying All Authors, 4 - Quit, 5 - Update, 6 - Cookie, 7 - session ");
out.println("<BR>Please enter IDB for editing:");
out.println("<TD>Nomer:</TD>");
out. println( "<TD><INPUT TYPE=TEXT NAME=nomer></TD>");
out.println("</TR>");
out.println("<TR>");
out.println("<TD>ID_B:</TD>");
out. println( "<TD><INPUT TYPE=TEXT NAME=ID_B></TD>");
out.println("</TR>");
out.println("<TR>");
out.println("<TD ALIGN=RIGHT COLSPAN=2>");
out.println("<INPUT TYPE=SUBMIT VALUE=Login></TD>");
out.println("</TR>");
out. println( "</TABLE>");
out.println("</FORM>");
out.println("</CENTER>");
out.println("</BODY>");
out.println("</HTML>");
}

public void doPost(HttpServletRequest request, 
HttpServletResponse response) throws ServletException, IOException {
response.setContentType("text/html");
PrintWriter out = response.getWriter();
String us= request.getParameter("userName");
String pass = request.getParameter("password"); 
String nom = request.getParameter("nomer"); 
out.println(soob+ "<BR>");//вывод сообщения
i=Integer.parseInt(nom);
if (login(us, pass))
 {
if (i==1)
response.sendRedirect("http://localhost:4444/myApp/WelcomeServlet7");
else
if (i==2)
response.sendRedirect("http://localhost:4444/myApp/WelcomeServlet8");
else
if (i==3)
response.sendRedirect("http://localhost:4444/myApp/WelcomeServlet9");
else
if (i==4){
//RequestDispatcher rd = request.getRequestDispatcher("WelcomeServlet6");
//rd.forward(request, response);
response.sendRedirect("http://localhost:4444/myApp/WelcomeServlet6");
}
else
if (i==6){
response.setContentType("text/html");
//посылаем Cookie браузеру
Cookie c1 = new Cookie("userName",us);
Cookie c2 =new Cookie("password",pass);
c1.setMaxAge(10000);
response.addCookie(c1);
response.addCookie(c2);
// response.sendRedirect здесь не работает, 
// используем тег Meta для переадресации на ContentServlet 
out.println("<META HTTP-EQUIV=Refresh CONTENT=0;URL=ContentServlet3>");
}
else
if (i==7){
//отправка session браузеру 
response.setContentType("text/html");
HttpSession session = request.getSession(true); 
session.setAttribute("loggedIn", new String("true")); 
out.println("<META HTTP-EQUIV=Refresh CONTENT=0;URL=ContentServlet2>");
//response.sendRedirect("http://localhost:4444/myApp/ContentServlet2");
}
else
{String IDCO = request.getParameter("ID_B"); 
id=Integer.parseInt(IDCO);
if(i==5){
response.setContentType("text/html");
out.println("<HTML>");
out.println("<HEAD>");
out.println("<TITLE>Edit selected row</TITLE>");
out.println("</HEAD>");
out.println("<BR><FORM METHOD=POST>");
out.println("<BODY>");
out.println("<CENTER>");
out.println("<BR>");
out. println("<BR><H2>Go to the editable line</H2>");
out.println("<BR>");
out.println("<BR>Please select a link to another page.");
out.println("<TABLE>");
out.println("<TR>");
out.println("<TD>Update row whith rewriting URL</TD>");
out. println("<TD><A HREF=UpdateServlet1?id=" + id +">Update1</A></TD>");
out.println("</TR>");
out.println("<TR>");
out.println("<TD>Update row whith hidden fields</TD>");
out.println("<TD><A HREF=UpdateServlet2?id=" + id +">Update2</A></TD>");
out.println("</TR>");
out.println("<TR>");
out.println("<TD>Go back to the LoginServSeans6 Page</TD>");
out. println("<TD><A HREF=LoginServSeans5>Go back</A></TD> ");
out.println("</TR>");
out. println( "</TABLE>");
out.println("<BR>");
out.println("</FORM>");
out.println("</CENTER>");
out.println("</BODY>");
out.println("</HTML>");
}}
}
else {
sendLoginForm(response, true);
}
}

boolean login(String userName, String password) { 
try
{
String url = "jdbc:mysql://localhost:3306/bookauthor";
con = DriverManager.getConnection(url, "root", "");
s= con.createStatement();
sql = "SELECT userName FROM Users WHERE UserName="+"'"+userName+"'"+" AND Password="+"'"+password+"'"; 
ResultSet rs = s.executeQuery(sql); 
if (rs.next()){ 
rs.close();
s.close();
con.close(); 
return true;
} 
rs.close();
s.close();
con.close();
return false;
}
catch (SQLException e) { 
soob=e.toString();
}
catch (Exception e) { 
soob=e.toString();
}
return false;
}}


