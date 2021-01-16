import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class SqlUtils {
	private static Connection con;
	public static Statement stmt;

	public static void init() {
		String db="ystu_software", user="ystu", password="root";
		var url = "jdbc:mysql://localhost:3306/%s?autoReconnect=true&useSSL=false".formatted(db);
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		System.out.println("<<CONNECTED to MySQL>>");
	}

	public static String getTableViewString(String query) throws SQLException {
		var rs = stmt.executeQuery(query);
		return getTableViewString(rs);
	}

	public static void execute(String query) throws SQLException {
		stmt.execute(query);
	}

	public static boolean isExists(String query) throws SQLException {
		return stmt.executeQuery(query).next();
	}

	public static String getTableViewString(ResultSet rs) throws SQLException {
		if (!rs.isBeforeFirst())
			return "";

		var tbl = new ArrayList<ArrayList<String>>();
		var rsmd = rs.getMetaData();
		var columnCount = rsmd.getColumnCount();
		// get headers
		var headerRow = new ArrayList<String>();
		for (int i = 1; i <= columnCount; i++) {
			headerRow.add(rsmd.getColumnName(i));
		}
		var aligns = new int[columnCount];
		tbl.add(headerRow);
		for (int i = 0; i < columnCount; i++) {
			aligns[i] = headerRow.get(i).length();
		}
		// get table
		while (rs.next()) {
			var row = new ArrayList<String>();
			for (var i = 1; i <= columnCount; i++) {
				var s = rs.getString(i);
				row.add(s);
				// align stuff
				if (s.length() > aligns[i - 1]) {
					aligns[i - 1] = s.length();
				}
			}
			tbl.add(row);
		}
		// output
		int rowCount = tbl.size();
		StringBuilder fmt = new StringBuilder();
		for (var align : aligns) {
			fmt.append("%-").append(align + 4).append("s");
		}
		StringBuilder s = new StringBuilder();
		s.append(String.format(fmt + "%n", headerRow.toArray()));
		s.append("-".repeat(Arrays.stream(aligns).reduce(0, (a, b) -> a + b + 4))).append("\n");
		for (int rowId = 1; rowId < rowCount; rowId++) {
			s.append(String.format(fmt + "%n", tbl.get(rowId).toArray()));
		}
		return s.toString();
	}
}
