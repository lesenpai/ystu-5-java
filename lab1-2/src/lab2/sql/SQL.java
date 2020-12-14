package lab2.sql;

import lab2.p1.DBType;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;
import static java.lang.System.out;

public class SQL {
	private Connection con;
	private Statement stmt;
	private DBType dbType;

	public SQL(DBType dbType, String db, String user, String password) throws SQLException {
		this.dbType = dbType;
		String url = "";
		switch (dbType) {
			case MySQL -> url = String.format("jdbc:mysql://localhost:3306/%s?autoReconnect=true&useSSL=false", db);
			case SQLServer -> url = String.format("jdbc:sqlserver://localhost;database=%s", db);
			case Derby -> url = String.format("jdbc:derby:d:/repos/db/derby/%s;create=true", db);
		}

		con = DriverManager.getConnection(url, user, password);
		out.printf("[Connected to %s]\n", dbType.name());
		stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	}

	public void close() throws SQLException {
		con.close();
		out.printf("[Disconnected from %s]\n", dbType.name());
	}

	public void runScript(Path path) throws IOException, SQLException {
		var sqlCommentSign = (dbType == DBType.MySQL) ? "#" : "--";

		String[] script =
			Arrays.stream(
				Arrays.stream(
					Files.readString(path).split("\n"))
					.filter(s -> !s.startsWith(sqlCommentSign)) // remove commented lines
					.toArray(String[]::new))
				.reduce("", String::concat)
				.split((dbType == DBType.Derby) ? ";" : ";(?!$)"); // derby's 'execute' dont like ';' at end

		for (var q : script) {
			execute(q);
		}
	}

	public void initDb() throws IOException, SQLException {
		switch (dbType) {
			case MySQL -> runScript(Path.of("./src/lab2/sql/mysql_init.sql"));
			case SQLServer -> runScript(Path.of("./src/lab2/sql/sqlserver_init.sql"));
			case Derby -> {
				var createTeamQuery = "CREATE TABLE Team" +
					"(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY," +
					"NAME VARCHAR(30) NOT NULL," +
					"PRIMARY KEY (ID)," +
					"UNIQUE (NAME))";
				var createPlayerQuery = "CREATE TABLE Player" +
					"(ID INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY," +
					"SURNAME VARCHAR(30) NOT NULL," +
					"NAME VARCHAR(30) NOT NULL," +
					"PATRONYMIC VARCHAR(30) NOT NULL," +
					"PRIMARY KEY (ID)," +
					"TeamID integer references Team(ID))";
				// CREATE IF NOT EXISTS <tables>
				try {
					execute("DROP TABLE Player");
					execute("DROP TABLE Team");
					execute(createTeamQuery);
					execute(createPlayerQuery);
					runScript(Path.of("./src/lab2/sql/derby_init_insert.sql"));
				}
				catch (SQLException e) {
					if (e.getMessage().endsWith("does not exist.")) {
						execute(createTeamQuery);
						execute(createPlayerQuery);
						runScript(Path.of("./src/lab2/sql/derby_init_insert.sql"));
					}
					else throw e;
				}
			}
		}
	}

	public DefaultTableModel buildTableModel(String query) throws SQLException {
		ResultSet rs = this.executeQuery(query);
		ResultSetMetaData metaData = rs.getMetaData();

		// names of columns
		Vector<String> columnNames = new Vector<>();
		int columnCount = metaData.getColumnCount();
		for (int column = 1; column <= columnCount; column++) {
			columnNames.add(metaData.getColumnName(column));
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

	public String getTableViewString(String query) throws SQLException {
		ResultSet rs = stmt.executeQuery(query);
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

	public void execute(String query, Object... args) throws SQLException {
		stmt.execute(String.format(query, Arrays.stream(args).toArray()));
	}

	public ResultSet executeQuery(String query, Object... args) throws SQLException {
		return stmt.executeQuery(String.format(query, Arrays.stream(args).toArray()));
	}

	public boolean exists(String table, String where, Object... args) throws SQLException {
		var query = String.format("SELECT 1 FROM %s WHERE %s", table, String.format(where, Arrays.stream(args).toArray()));
		return stmt.executeQuery(query).next();
	}

	public boolean existsQuery(String query, Object... args) throws SQLException {
		query = String.format(query, Arrays.stream(args).toArray());
		return stmt.executeQuery(query).next();
	}

	public void printDbSysInfo() throws SQLException {
		out.println("[DB info]");
		switch (dbType) {
			case MySQL -> {
				var s = getString("SHOW VARIABLES WHERE variable_name IN ('version','version_comment')").split("\n");
				out.println(s[1].split("\t")[1] + " - " + s[0].split("\t")[1]);
				out.print(getString("select current_date(),current_time();"));
			}
			case SQLServer -> {
				out.println(getString("SELECT @@VERSION").split("\n")[0]);
				out.print(getString("SELECT GETDATE()"));
			}
			case Derby -> {
				var dbmd = con.getMetaData();
				String productName = dbmd.getDatabaseProductName();
				String productVersion = dbmd.getDatabaseProductVersion();
				out.println(productName + " " + productVersion);
				out.print(getString("VALUES CURRENT_TIMESTAMP"));
			}
		}
	}

	public String getString(String query) throws SQLException {
		StringBuilder s = new StringBuilder();
		ResultSet rs = stmt.executeQuery(query);
		var columnCount = rs.getMetaData().getColumnCount();
		while (rs.next()) {
			for (var i = 1; i <= columnCount; i++) {
				s.append(rs.getString(i)).append("\t");
			}
			s.append("\n");
		}
		return s.toString();
	}
}
