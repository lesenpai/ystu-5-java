import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value = "/manufacturer")
public class ManufacturerViewServlet extends HttpServlet {

	@Override
	public void init() {
		SqlUtils.init();
	}

	private void sendView(HttpServletResponse response, String filter, String lastAction, String errorStr) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Manufacturers View</title>");
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
						background-color: #3F91EF;
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
		out.println("<h1>Manufacturers View</h1>");
		if (lastAction != null) {
			out.println("<p>Last action: %s</p>".formatted(lastAction));
		}
		if (errorStr != null) {
			out.println("<p>Error: %s</p>".formatted(errorStr));
		}
		out.println("""
   			<form method="POST">
				<table>
					<tr><td colspan=2 align=center>Add Manufacturer</td></tr>
					<tr>
						<td>Name</td>
						<td><input type="text" name="manufacturerName" value=""></td>
					</tr>
					<tr>
						<td colspan=2 align=center><input type="submit" value="Add"><td>
					</tr>
				</table>
				<input type="hidden" name="form-name" value="add-manufacturer">
			</form>
			
			<form method="POST">
				<table>
					<tr>
						<td colspan=2 align=center>Delete Manufacturer</td>
					</tr>
					<tr>
						<td>ID</td>
						<td><input type="text" name="manufacturerId" value=""></td>
					</tr>
					<tr>
						<td colspan=2 align=center><input type="submit" value="Delete"><td>
					</tr>
				</table>
				<input type="hidden" name="form-name" value="delete-manufacturer">
			</form>
			
			<form method="POST">
				<table>
					<tr><td colspan=2 align=center>View Manufacturers</td></tr>
					<tr>
						<td>Keyword</td>
						<td><input type="text" name="keyword" value=""></td>
					</tr>
					<tr>
						<td colspan=2 align=center><input type="submit" value="View"><td>
					</tr>
				</table>
				<input type="hidden" name="form-name" value="view-manufacturer">
			</form>
			""");
		try {
			var query = "SELECT * FROM Manufacturer WHERE concat_ws('-',id,name) LIKE '%s'".formatted(filter);
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
				case "view-manufacturer" -> {
					var filter = "%" + request.getParameter("keyword") + "%";
					sendView(response, filter, "view", null);
				}

				case "add-manufacturer" -> {
					var manName = request.getParameter("manufacturerName");

					String errorStr = null;
					if (SqlUtils.isExists("SELECT 1 FROM "+MyConst.Table_Manufacturer+" WHERE name='%s'".formatted(manName))) {
						errorStr = "такой производитель уже существует";
					}
					else {
						SqlUtils.execute("INSERT INTO "+MyConst.Table_Manufacturer+" (name) VALUES ('%s')".formatted(manName));
					}
					sendView(response, MyConst.MySql_filterAll, formName, errorStr);
				}

				case "delete-manufacturer" -> {
					var manId = request.getParameter("manufacturerId");

					String errorStr = null;
					if (SqlUtils.isExists("SELECT 1 FROM "+MyConst.Table_Manufacturer+" WHERE id=%s".formatted(manId))) {
						if (SqlUtils.isExists("SELECT 1 FROM "+MyConst.Table_Product+" WHERE man_id=%s".formatted(manId))) {
							errorStr = "к производителю привязаны продукты";
						}
						else {
							SqlUtils.execute("DELETE FROM "+MyConst.Table_Manufacturer+" WHERE id=%s".formatted(manId));
						}
					}
					else {
						errorStr = "производитель не найден";
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
