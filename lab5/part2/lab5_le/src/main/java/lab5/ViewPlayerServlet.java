package lab5;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value = "/view-player")
public class ViewPlayerServlet extends HttpServlet {

	@Override
	public void init() {
		SqlUtils.init();
	}

	private void sendView(HttpServletResponse response, String keyword, String teamId, String lastAction, String errorStr) throws IOException {
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
		out.println("<h1>Player view</h1>");
		if (lastAction != null) {
			out.println("<p>Last action: %s</p>".formatted(lastAction));
		}
		if (errorStr != null) {
			out.println("<p>Error: %s</p>".formatted(errorStr));
		}
		out.println("""
			<form method="POST">
				<table>
				<tr><td colspan=2 align=center>Delete player</td></tr>
				<tr>
					<td>Row ID</td>
					<td><input type="text" name="playerId" value=""></td>
				</tr>
				<tr><td colspan=2 align=center><input type="submit" value="Delete"><td></tr>
				</table>
				<input type="hidden" name="form-name" value="delete-player">
			</form>
			<form method="POST">
				<table>
					<tr><td colspan=2 align=center>Add player</td></tr>
					<tr>
						<td>Surname</td>
						<td><input type="text" name="playerSurname" value=""></td>
					</tr>
					<tr>
						<td>Name</td>
						<td><input type="text" name="playerName" value=""></td>
					</tr>
					<tr>
						<td>Patronymic</td>
						<td><input type="text" name="playerPatronym" value=""></td>
					</tr>
					<tr>
						<td>Team ID</td>
						<td><input type="text" name="teamId" value=""></td>
					</tr>
					<tr><td colspan=2 align=center><input type="submit" value="Add"><td></tr>
				</table>
				<input type="hidden" name="form-name" value="add-player">	
			</form>
			<form method="POST">
				<table>
					<tr><td colspan=2 align=center>View players</td></tr>
					<tr>
						<td>Keyword</td>
						<td><input type="text" name="keyword" value=""></td>
					</tr>
					<tr>
						<td>Team ID</td>
						<td><input type="text" name="teamId" value=""></td>
					</tr>
					<tr><td colspan=2 align=center><input type="submit" value="View"><td></tr>
				</table>
				<input type="hidden" name="form-name" value="view-player">
			</form>
			""");
		try {
			var query = "select * from Player where teamId like '%s' and concat_ws('-',surname,name,patronym) like '%s'".formatted(teamId, keyword);
			var rs = SqlUtils.stmt.executeQuery(query);
			out.println("<table id=table class=table-view>");
			out.println(("<tr>" + "<th>%s</th>".repeat(5) + "</tr>").formatted("ID", "Surname", "Name", "Patronymic", "Team ID"));
			while (rs.next()) {
				out.println(("<tr>" + "<td>%s</td>".repeat(5) + "</tr>").formatted(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
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
		sendView(response, MyConst.MySql_filterAll, MyConst.MySql_filterAll, null, null);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		var formName = request.getParameter("form-name");

		try {
			switch (formName) {
				case "view-player" -> {
					var keyword = "%" + request.getParameter("keyword") + "%";
					var teamId = "%" + request.getParameter("teamId") + "%";
					sendView(response, keyword, teamId, "view", null);
				}
				case "add-player" -> {
					var pSurname = request.getParameter("playerSurname");
					var pName = request.getParameter("playerName");
					var pPatronym = request.getParameter("playerPatronym");
					var tId = request.getParameter("teamId");

					String errorStr = null;
					if (SqlUtils.isExists("select 1 from Team where id=%s".formatted(tId))) {
						if (SqlUtils.isExists("select 1 from Player where surname='%s' and name='%s' and patronym='%s' and teamId=%s".formatted(pSurname, pName, pPatronym, tId))) {
							errorStr = "такой игрок уже есть";
						}
						else {
							SqlUtils.execute("insert into Player (surname, name, patronym, teamId) values ('%s','%s','%s',%s)".formatted(pSurname, pName, pPatronym, tId));
						}
					}
					else {
						errorStr = "команда не найдена";
					}
					sendView(response, MyConst.MySql_filterAll, MyConst.MySql_filterAll, formName, errorStr);
				}
				case "delete-player" -> {
					var pId = request.getParameter("playerId");

					String errorStr = null;
					if (SqlUtils.isExists("select 1 from Player where id=%s".formatted(pId))) {
						SqlUtils.execute("delete from Player where id=%s".formatted(pId));
					}
					else {
						errorStr = "игрок не найден";
					}
					sendView(response, MyConst.MySql_filterAll, MyConst.MySql_filterAll, formName, errorStr);
				}
				default -> sendView(response, MyConst.MySql_filterAll, MyConst.MySql_filterAll, "default", null);
			}
		}
		catch (SQLException ex) {
			ex.printStackTrace();
			sendView(response, MyConst.MySql_filterAll, MyConst.MySql_filterAll, formName, ex.getMessage());
		}
	}
}
