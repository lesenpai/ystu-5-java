<%@ page session="false" %>
<jsp:useBean id="loginBean" scope="page" class="model1.LoginBean" />
<%
if (loginBean.login(request.getParameter("username"), 
	request. getParameter("password")))
	request.getRequestDispatcher("Welcome.jsp").forward(request, response); 
else
	response.sendRedirect("Login.jsp?error=yes");
%>
