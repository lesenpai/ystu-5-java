package lab5.part2;

import java.io.*;
import java.sql.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(value = "/product")
public class ProductViewServlet extends HttpServlet {

	@Override
	public void init() {
		SqlUtils.init();
	}

	private void sendView(HttpServletResponse response, String keyword, String teamId, String lastAction, String errorStr) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head>");
		out.println("<title>Products View</title>");
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
		out.println("<h1>Products View</h1>");
		if (lastAction != null) {
			out.println("<p>Last action: %s</p>".formatted(lastAction));
		}
		if (errorStr != null) {
			out.println("<p>Error: %s</p>".formatted(errorStr));
		}
		out.println("""
            <form method="POST">
            <input type="hidden" name="form-name" value="delete-product">
                <table>
                <tr>
                	<td colspan=2 align=center>Delete Product</td>
                </tr>
                <tr>
                    <td>ID</td>
                    <td><input type="text" name="productId" value=""></td>
                </tr>
                <tr>
                	<td colspan=2 align=center><input type="submit" value="Delete"><td>
                </tr>
                </table>
            </form>

            <form method="POST">
            <input type="hidden" name="form-name" value="add-product">
                <table>
                    <tr>
                    	<td colspan=2 align=center>Add Product</td>
                    </tr>
                    <tr>
                        <td>Name</td>
                        <td><input type="text" name="productName" value=""></td>
                    </tr>
                    <tr>
                        <td>Manufacturer ID</td>
                        <td><input type="text" name="manufacturerId" value=""></td>
                    </tr>
                    <tr>
                        <td colspan=2 align=center><input type="submit" value="Add"><td>
                    </tr>
                </table>   
            </form>

            <form method="POST">
            <input type="hidden" name="form-name" value="view-product">
                <table>
                    <tr>
                        <td colspan=2 align=center>View Products</td>
                    </tr>
                    <tr>
                        <td>Keyword</td>
                        <td><input type="text" name="keyword" value=""></td>
                    </tr>
                    <tr>
                        <td>Manufacturer ID</td>
                        <td><input type="text" name="manufacturerId" value=""></td>
                    </tr>
                    <tr>
                    	<td colspan=2 align=center><input type="submit" value="View"><td>
                    </tr>
                </table>
            </form>
            """);
		try {
			var query = "SELECT * FROM Product WHERE concat_ws('-',name) LIKE '%s'".formatted(keyword);
			var rs = SqlUtils.stmt.executeQuery(query);
			out.println("<table id=table class=table-view>");
			out.println(("<tr>" + "<th>%s</th>".repeat(3) + "</tr>").formatted("ID", "Name", "Manufacturer ID"));
			while (rs.next()) {
				out.println(("<tr>" + "<td>%s</td>".repeat(3) + "</tr>").formatted(rs.getString(1), rs.getString(2), rs.getString(3)));
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
				case "view-product" -> {
					var keyword = "%" + request.getParameter("keyword") + "%";
					var manId = "%" + request.getParameter("manufacturerId") + "%";
					sendView(response, keyword, manId, "view", null);
				}

				case "add-product" -> {
					var pName = request.getParameter("productName");
					var manId = request.getParameter("manufacturerId");

					String errorStr = null;
					if (SqlUtils.isExists("SELECT 1 FROM Manufacturer WHERE id=%s".formatted(manId))) {
						if (SqlUtils.isExists("SELECT 1 FROM Product WHERE name='%s' AND man_id=%s".formatted(pName, manId))) {
							errorStr = "такой продукт уже есть";
						}
						else {
							SqlUtils.execute("INSERT INTO Product (name, man_id) VALUES ('%s',%s)".formatted(pName, manId));
						}
					}
					else {
						errorStr = "производитель не найден";
					}
					sendView(response, MyConst.MySql_filterAll, MyConst.MySql_filterAll, formName, errorStr);
				}

				case "delete-product" -> {
					var pId = request.getParameter("productId");

					String errorStr = null;
					if (SqlUtils.isExists("SELECT 1 FROM Product WHERE id=%s".formatted(pId))) {
						SqlUtils.execute("DELETE FROM Product WHERE id=%s".formatted(pId));
					}
					else {
						errorStr = "продукт не найден";
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
