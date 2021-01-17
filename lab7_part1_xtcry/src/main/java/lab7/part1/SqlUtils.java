package lab7.part1;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class SqlUtils {

	private static Connection con;
	public static Statement stmt;

	public static void init() {
		String db = "ystu_software", user = "ystu", password = "root";
		var url = "jdbc:mysql://localhost:3306/%s?autoReconnect=true&useSSL=false".formatted(db);
		try {
			con = DriverManager.getConnection(url, user, password);
			stmt = con.createStatement();
		}
		catch (SQLException ex) {
			ex.printStackTrace();
		}
		System.out.println("/ Connected to MySQL");
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

	public static DefaultTableModel buildTableModel(String query, Vector<String> columnNames) throws SQLException {
		ResultSet rs = stmt.executeQuery(query);
		ResultSetMetaData metaData = rs.getMetaData();
		int columnCount = metaData.getColumnCount();

		// names of columns
		if (columnNames == null) {
			columnNames = new Vector<String>();
			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		return new DefaultTableModel(data, columnNames);
	}

	public static DefaultTableModel buildTableModel(String query) throws SQLException {
		return buildTableModel(query, null);
	}

	public static String getTableViewString(ResultSet rs) throws SQLException {
		if (!rs.isBeforeFirst()) {
			return "";
		}

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

	private ResultSet executeQuery(String query) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
