package lab2.p2.test;

import java.sql.*;
import static java.lang.System.out;

public class TestJavaDB {
	public static void main(String[] args) {
		try {
			Connection con = DriverManager.getConnection("jdbc:derby:d:/repos/db/javadb;create=true");
			out.println("[Connected to JavaDB]");
			DatabaseMetaData dbmd = con.getMetaData();
			String productName = dbmd.getDatabaseProductName();
			String productVersion = dbmd.getDatabaseProductVersion();
			out.println(productName+" "+productVersion);
		}
		catch (SQLException throwables) {
			out.println("[SQL ERROR]");
			throwables.printStackTrace();
		}
	}
}
