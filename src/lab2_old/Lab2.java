package lab2_old;

import java.sql.*;
import static java.lang.System.*;
import java.util.Scanner;

public class Lab2 {
    public static void main(String[] args) throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String db = "products", user = "le", password = "ledb", url = String.format("jdbc:sqlserver://localhost;database=%s;user=%s;password=%s",db,user,password);

        try (Connection con = DriverManager.getConnection(url); Statement stmt = con.createStatement();) {
            out.println("[Connected]");
            var in = new Scanner(System.in);

            while(true) {
                out.println("type sql query:");
                //var input = in.nextLine();
                //String sql = "SELECT * FROM Product WHERE CategoryID=1";
                //String sql = "SELECT * FROM Category";
                try {
                    String input = in.nextLine();
                    //var input = "select * from Category";
                    if(input.equals("exit"))
                        return;
                    ResultSet rs = stmt.executeQuery(input);
                    var columnCount = rs.getMetaData().getColumnCount();

                    while (rs.next()) {
                        var s="";
                        for(var i=1;i<=columnCount;i++) {
                            //out.println(rs.getString(i)+"\t");
                            s+=rs.getString(i) + " ";
                        }
                        out.println(s);
                        //out.println(rs.getString("name"));
                    }
                }
                catch (SQLException e) {
                    out.println("<input error>");
                    out.println(e.getMessage());
                }
            }
        }
        catch (SQLException e) {
            out.println("ERROR");
            e.printStackTrace();
        }
    }
}