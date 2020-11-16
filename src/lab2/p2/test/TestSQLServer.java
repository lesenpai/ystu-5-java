package lab2.p2.test;

import java.sql.*;
import static java.lang.System.out;

public class TestSQLServer {
	public static void main(String[] args) throws ClassNotFoundException {
		String db = "test", user = "le", password = "ledb",
			url = String.format(
				"jdbc:sqlserver://localhost;database=%s;user=%s;password=%s",
				db,user,password);

		try {
			Connection con = DriverManager.getConnection(url);
			out.println("[Connected]");
			var  stmt = con.createStatement();
			var rs = stmt.executeQuery("SELECT @@VERSION");
			var columnCount = rs.getMetaData().getColumnCount();
			while(rs.next()) {
				var s = "";
				for(var i=1; i<=columnCount; i++) {
					s += rs.getString(i) + " ";
				}
				out.println(s.replace("\t",""));
			}
		}
		catch (SQLException e) {
			out.println("SQL ERROR");
			e.printStackTrace();
		}
	}
}
