package lab2.p1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;
import static java.lang.System.out;

public class Lab2 {
    public static void main(String[] args) {
        /*
        * commands:
        * - select all teams
        * - select all players
        * - select players of <team_name>
        * - add player <player_name> <team_name>
        * - add team <team_name>
        * - delete team <team_name>
        * - delete player <player_name> <team_name>
        * */
        final String
            cmdHelp = "help",
            cmdSelectAllTeams = "sel-all-teams",
            cmdSelectAllPlayers = "sel-all-pls",
            cmdSelectPlayersOfTeam = "sel-pls-of",
            cmdAddPlayer = "add-pl",
            cmdAddTeam = "add-team",
            cmdDeleteTeam = "del-team",
            cmdDeletePlayer = "del-pl";
        final String tblTeam = "Team", tblPlayer = "Player";


        String db = "football_players",
            url = "jdbc:mysql://localhost:3306/"+db+"?autoReconnect=true&useSSL=false",
            user = "le", password = "ledb";

        try (Connection con = DriverManager.getConnection(url, user, password); Statement stmt = con.createStatement();) {
            out.println("[Connected]");
            initDb(stmt);
            var in = new Scanner(System.in);

            while (true) {
                out.println("Type command:");
                var cmdArgs = in.nextLine().split("\\s+");
                if (cmdArgs.length == 0) {
                    out.println("Error: empty string");
                    continue;
                }
                String input = cmdArgs[0];
                switch (input) {
                    case cmdSelectAllTeams -> {
                        printListRs(stmt.executeQuery("SELECT * FROM Team"));
                    }
                }
            }



        }
        catch (SQLException | IOException e) {
            out.println("[ERROR]");
            e.printStackTrace();
        }
    }

    private static void printListRs(ResultSet rs) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            for (int i=1; i<columnCount; i++) {
                out.print(rs.getString(i)+"\t");
            }
            out.println();
        }
    }

    private static void initDb(Statement stmt) throws SQLException, IOException {
        var rs = stmt.executeQuery("SHOW TABLES LIKE 'Team';");
        if (!rs.next()) {
            String sql = Files.readString(Paths.get("src/lab2/p1/init_db.sql"));
            stmt.execute(sql);
        }
    }
}
