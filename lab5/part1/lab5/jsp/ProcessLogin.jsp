<%@ page session="false" %>
<jsp:useBean id="loginBean" scope="page" class="model1.LoginBean" />
 <%
if (loginBean.login(request.getParameter("userName"), 
request. getParameter("password")))
request.getRequestDispatcher("Welcome.jsp").forward(request, response); 
else
//�� ������ ������������ sendRedirect, ��� ��� ����� ������� ����� ?�����
//�� �������� Login.jsp.
//��������� ����������� RequestDispatcher.forward(), URL ��-�������� 
// ����� ������� URL.
response.sendRedirect("Login.jsp?error=yes");
%>
