<%@ page import="java.util.*, java.text.*" %>
<%@ page contentType="text/html; charset=windows-1251" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<center>
    <%
        String error = request.getParameter("error");
        if (error != null) {
            out.println(error);
        }
    %>
    <br>
    <br><h2>Login</h2>
    <br>
    <br>Please enter your username and password.
    <br>
    <br>
    <form method=POST>
        <table>
            <tr>
                <td>Username:</td>
                <td><input type=text name=username></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type=password name=password></td>
            </tr>
            <tr>
                <td><input type=submit name='login' value='Login'></td>
                <td align=right><input type=submit name='createUser' value='Create user'></td>
            </tr>
        </table>
    </form>
</center>
</body>
</html>
<%
    var username = request.getParameter("username");
    var password = request.getParameter("password");
    System.out.printf("user=%s, pass=%s %n", username, password);
%>