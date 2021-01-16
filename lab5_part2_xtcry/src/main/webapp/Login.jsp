<%@ page import="java.util.*, java.text.*" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="lab5.part2.MyConst" %>
<%@ page import="lab5.part2.SqlUtils" %>
<%@ page contentType="text/html; charset=windows-1251" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        .div-center {
            margin: 0;
            position: absolute;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
        }
    </style>
</head>
<body>
<div class="div-center" style="text-align: center">
    <%
        SqlUtils.init();

        String msg = request.getParameter("msg");
        if (msg != null) {
            out.println(msg);
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
</div>
</body>
</html>
<%
    final String USERNAME_REGEX = "[a-zA-Z][a-zA-Z0-9_]{3,32}";
    String url = null;

    if (request.getParameter("login") != null) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        try {
            // check if user exists -> go to action page
            if (SqlUtils.isExists("SELECT 1 FROM User WHERE username='%s' AND password='%s'".formatted(username, password))) {
                url = MyConst.UrlRoot+"action";
            }
            else {
                url = MyConst.UrlRoot+"/Login.jsp?msg=Error! Wrong username/password";
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        response.sendRedirect(url);
    }
    else if (request.getParameter("createUser") != null) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // check if user already exists
            if (SqlUtils.isExists("SELECT 1 FROM User WHERE username='%s' AND password='%s'".formatted(username, password))) {
                url = MyConst.UrlRoot+"/Login.jsp?error=Error! User '%s' already exists".formatted(username);
            }
            // user not exists -> try create user
            else {
                // check name to regex
                if (username.matches(USERNAME_REGEX)) {
                    SqlUtils.execute("INSERT INTO User (username, password) VALUES ('%s','%s')".formatted(username, password));
                    url = MyConst.UrlRoot+"/Login.jsp?msg=User '%s' added".formatted(username);
                }
                // bad username
                else {
                    url = MyConst.UrlRoot+"/Login.jsp?msg=Error! Bad username. Username must be 4-30 long and must start with a letter, can contain letters, numbers and '_'";
                }
            }
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }

        response.sendRedirect(url);
    }
%>
