<%@ page import="java.util.Enumeration" %> 
<HTML>
<HEAD><TITLE>Using JSP</TITLE></HEAD>
<BODY BGCOL0R=#DADADA>
<%
//Получение имен параметров
Enumeration parameters = request.getParameterNames();
String param = null;
while (parameters.hasMoreElements()) {
param = (String) parameters.nextElement();
out.println(param + ":" + request. getParameter(param) + "<BR>");
}
out.close();
 %>
</BODY> 
</HTML>
