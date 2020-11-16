package lab2.p2.ui;

import java.sql.*;
import static java.lang.System.out;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class SQL {

	private Statement stmt;

	public SQL(String url) throws Exception {
		try {
			Connection connection = DriverManager.getConnection(url);
			out.println("[Connected]");
			stmt = connection.createStatement();
		} catch (SQLException e) {
			out.println("[ERROR]");
			e.printStackTrace();
		}
	}

	public String getString(String sql) throws SQLException {
		ResultSet rs = stmt.executeQuery(sql);
		var s = "";
		var columnCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			for (var i = 1; i <= columnCount; i++) {
				s += rs.getString(i) + "; ";
			}
			s += "\n";
		}
		return s;
	}

	public ResultSet getResultSet(String sql) throws SQLException {
		return stmt.executeQuery(sql);
	}

	public void execute(String sql) throws SQLException {
		stmt.execute(sql);
	}

	public DefaultTableModel buildTableModel(String sql) throws SQLException {
		ResultSet rs = this.getResultSet(sql);
		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		return new DefaultTableModel(data, columnNames);
	}

	public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<String>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
		}

		// data of the table
		Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		while (rs.next()) {
			Vector<Object> vector = new Vector<Object>();
			for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
				vector.add(rs.getObject(columnIndex));
			}
			data.add(vector);
		}

		return new DefaultTableModel(data, columnNames);
	}

	public boolean exists(String sql) throws SQLException {
		return stmt.executeQuery(sql).next();
	}

	public String[] getColumnNames(String table) throws SQLException {
		ResultSet rs = stmt.executeQuery("SELECT TOP 1 * FROM " + table);
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnCount = rsmd.getColumnCount();
		String[] lst = new String[columnCount];
		rs.next();
		for (int i = 0; i < columnCount; i++) {
			lst[i] = rsmd.getColumnName(i + 1);
		}
		return lst;
	}
}
