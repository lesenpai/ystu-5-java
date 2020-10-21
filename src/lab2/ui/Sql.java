package lab2.ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import static java.lang.System.out;

public class Sql {
    private Statement statement;

    public Sql() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String db = "products", user = "le", password = "ledb",
                url = String.format("jdbc:sqlserver://localhost;database=%s;user=%s;password=%s",db,user,password);

        try {
            Connection connection = DriverManager.getConnection(url);
            statement = connection.createStatement();
            out.println("[Connected]");
        }
        catch (SQLException e) {
            out.println("ERROR");
            e.printStackTrace();
        }
    }

    public String getString(String sql) throws SQLException {
        ResultSet rs = statement.executeQuery(sql);
        var s = "";
        var columnCount = rs.getMetaData().getColumnCount();
        while(rs.next()) {
            for(var i=1;i<=columnCount;i++) {
                s+=rs.getString(i) + "; ";
            }
            s+="\n";
        }
        return s;
    }

    public ResultSet getResultSet(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    public void query(String sql) throws SQLException {
        statement.execute(sql);
    }

    public ObservableList<ObservableList<String>> getTableData(String tableName) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT count(*) FROM "+tableName);
        rs.next();
        int rowCount = rs.getInt(1);
        rs = statement.executeQuery("SELECT * FROM "+tableName);
        int columnCount = rs.getMetaData().getColumnCount();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int col=1;col<=columnCount;col++) {
                row.add(rs.getString(col).trim());
            }
            data.add(row);
        }

        return data;
    }

    public String[] getColumnNames(String table) throws SQLException {
        ResultSet rs = statement.executeQuery("SELECT TOP 1 * FROM "+table);
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        String[] lst = new String[columnCount];
        rs.next();
        for (int i = 0; i < columnCount; i++ ) {
            lst[i] = rsmd.getColumnName(i+1);
        }
        return lst;
    }
}
