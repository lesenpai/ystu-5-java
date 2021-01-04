<%@ page import="java.util.*, java.text.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>
        Таблица пользователей
    </title>
</head>
 
<body>
    <h1>Таблица с данными</h1>

    <h2>Добавить пользователя</h2>
    <%-- Форма заполнения данных --%>
    <FORM name="form1" method="post">
        Имя: <INPUT type="text" name="name" size="20" maxlength="20"><BR>
        E-mail: <INPUT type="text" name="email" size="20" maxlength="20">
        <BR><BR><BR>        
        <INPUT type="submit" name="submit" value="Добавить"><BR>
    </FORM>

    <h2>Таблица с пользователями</h2> 

    <%-- инициализация --%>
    <%! boolean flagStart = true; %>
    <%! List<String> name_array = new ArrayList<String>(); %>
    <%! List<String> email_array = new ArrayList<String>(); %>
    <%! List<Date> reg_date_array = new ArrayList<Date>(); %> 

    <table border="1px" cellpadding="8px">
        <%-- Названия колонок в таблице --%>
        <tr>
            <th>Имя</th>
            <th>E-mail</th>
            <th>Дата регистрации</th>
        </tr>
        <%-- Считываем параметры запроса --%>
        <% if(!flagStart){ %>
            <%  name_array.add(request.getParameter("name"));
                email_array.add(request.getParameter("email"));
                reg_date_array.add(new Date()); %>      
             
                <%-- Заполняем таблицу --%>
                <% for(int i = 0; i < name_array.size(); ++i){
                    out.println("<tr>");
                    out.println("<td>" + name_array.get(i) + "</td>");
                    out.println("<td>" + email_array.get(i) + "</td>");
                    out.println("<td>" + reg_date_array.get(i) + "</td>");  
                    out.println("</tr>");
                } %>
        <% } else
                flagStart = false; %>
    </table>
</body>
</html>
