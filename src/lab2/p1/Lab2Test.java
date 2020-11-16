package lab2.p1;

import lab2.sql.SQL;

import java.sql.SQLException;

import static java.lang.System.out;

public class Lab2Test {
	public static void main(String[] args) {
		try {
			var dbTypes = new DBType[] { DBType.MySQL, DBType.SQLServer, DBType.Derby };

			// choose mysql/mssql/javadb
			for (var dbType : dbTypes) {
				SQL sql;
				try {
					sql = new SQL(dbType, "test", "le", "ledb");
				}
				catch (SQLException e) {
					out.println("[Connection ERROR]");
					out.println(e.getMessage());
					continue;
				}
				// go
				try {
					sql.printDbSysInfo();

					// init db
					switch (dbType) {
						case MySQL -> {

						}
					}

					sql.close();
					out.println("[Connection closed]\n");
				}
				catch (SQLException e) {
					out.println("[SQL ERROR]");
					out.println(e.getMessage());
				}
			}
		}
		catch (Exception e) {
			out.println("[ERROR]");
			out.println(e.getMessage());
		}
	}
}
