<%@ page session="false" %>
<jsp:useBean id="loginBean" scope="page" class="model1.LoginBean" />
 <%
if (loginBean.login(request.getParameter("userName"), 
request. getParameter("password")))
request.getRequestDispatcher("Welcome.jsp").forward(request, response); 
else
//ћы должны использовать sendRedirect, так как хотим послать часть ?еггог
//на страницу Login.jsp.
//ѕоскольку примен€етс€ RequestDispatcher.forward(), URL по-прежнему 
// будет текущим URL.
response.sendRedirect("Login.jsp?error=yes");
%>
