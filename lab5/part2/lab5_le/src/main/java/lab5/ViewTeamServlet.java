package lab5;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value = "/view-team")
public class ViewTeamServlet extends HttpServlet {

	@Override
	public void init() {
		SqlUtils.init();
	}

	private void sendView(HttpServletResponse response, String filter, String lastAction, String errorStr) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Action</title>");
		out.println("<meta charset=\"UTF-8\">");
		out.println("""
			 	<style>
			 		.table-view {
			 			border: 1px solid black;
			 		}
			 	
			 		.table-view th {
			   			font-weight: bold;
			   			border: 1px #DDD solid; 
						padding: 5px; 
						background-color: #DDD;
			   		}	
			 	
					.table-view td {
						border: 1px #DDD solid; 
						padding: 5px; 
					}   
					
					.selected {
						background-color: brown;
						color: #FFF;
					}
				</style>
			"""
		);
		out.println("<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js\"></script>");
		out.println("""
			<script>
				$(document).ready(()=>{
					$("#table tr").click(function(){
			    		$(this).addClass('selected').siblings().removeClass('selected');
			 		});	
				})
			</script>
			"""
		);
		out.println("</head>");
		out.println("<body>");
		out.println("<center>");
		out.println("<h1>Team view</h1>");
		if (lastAction != null) {
			out.println("<p>Last action: %s</p>".formatted(lastAction));
		}
		if (errorStr != null) {
			out.println("<p>Error: %s</p>".formatted(errorStr));
		}
		out.println("""
   			<form method="POST">
				<table>
					<tr><td colspan=2 align=center>Add team</td></tr>
					<tr>
						<td>Name</td>
						<td><input type="text" name="teamName" value=""></td>
					</tr>
					<tr><td colspan=2 align=center><input type="submit" value="Add"><td></tr>
				</table>
				<input type="hidden" name="form-name" value="add-team">
			</form>
			<form method="POST">
				<table>
					<tr><td colspan=2 align=center>Delete team</td></tr>
					<tr>
						<td>Row ID</td>
						<td><input type="text" name="teamId" value=""></td>
					</tr>
					<tr><td colspan=2 align=center><input type="submit" value="Delete"><td></tr>
				</table>
				<input type="hidden" name="form-name" value="delete-team">
			</form>
			<form method="POST">
				<table>
					<tr><td colspan=2 align=center>View teams</td></tr>
					<tr>
						<td>Keyword</td>
						<td><input type="text" name="keyword" value=""></td>
					</tr>
					<tr><td colspan=2 align=center><input type="submit" value="View"><td></tr>
				</table>
				<input type="hidden" name="form-name" value="view-team">
			</form>
			""");
		try {
			var query = "select * from Team where concat_ws('-',id,name) like '%s'".formatted(filter);
			var rs = SqlUtils.stmt.executeQuery(query);
			out.println("<table id=table class=table-view>");
			out.println("<tr><th>ID</th><th>Name</th></tr>");
			while (rs.next()) {
				out.println(("<tr>" + "<td>%s</td>".repeat(2) + "</tr>").formatted(rs.getString(1), rs.getString(2)));
			}
			out.println("</table>");
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		out.println("</center>");
		out.println("</body>");
		out.println("</html>");
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		sendView(response, MyConst.MySql_filterAll, null, null);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		var formName = request.getParameter("form-name");

		try {
			switch (formName) {
				case "view-team" -> {
					var filter = "%" + request.getParameter("keyword") + "%";
					sendView(response, filter, "view", null);
				}
				case "add-team" -> {
					var tName = request.getParameter("teamName");

					String errorStr = null;
					if (SqlUtils.isExists("select 1 from Team where name='%s'".formatted(tName))) {
						errorStr = "такая команда уже существует";
					}
					else {
						SqlUtils.execute("insert into Team (name) values ('%s')".formatted(tName));
					}
					sendView(response, MyConst.MySql_filterAll, formName, errorStr);
				}
				case "delete-team" -> {
					var tId = request.getParameter("teamId");

					String errorStr = null;
					if (SqlUtils.isExists("select 1 from Team where id=%s".formatted(tId))) {
						if (SqlUtils.isExists("select 1 from Player where teamId=%s".formatted(tId))) {
							errorStr = "к команде привязаны игроки";
						}
						else {
							SqlUtils.execute("delete from Team where id=%s".formatted(tId));
						}
					}
					else {
						errorStr = "команда не найдена";
					}
					sendView(response, MyConst.MySql_filterAll, formName, errorStr);
				}
				default -> sendView(response, MyConst.MySql_filterAll, "default", null);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			sendView(response, MyConst.MySql_filterAll, formName, ex.getMessage());
		}
	}
}
