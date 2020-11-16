package lab2.p2.test;

import java.sql.*;

import static java.lang.System.out;

public class TestMySQL {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/test?autoReconnect=true&useSSL=false",
                user = "le", password = "ledb";

        try (Connection con = DriverManager.getConnection(url, user, password); Statement stmt = con.createStatement();) {
            out.println("[Connected to MySQL]");
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            int columnCount = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                for (int i=1; i<=columnCount; i++) {
                    out.print(rs.getString(i)+" ");
                }
                out.println();
            }

            /*while(true) {
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
            }*/
        }
        catch (SQLException e) {
            out.println("[ERROR]");
            e.printStackTrace();
        }
    }
}