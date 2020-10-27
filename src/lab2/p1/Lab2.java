package lab2.p1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Scanner;
import static java.lang.System.out;

public class Lab2 {
    private static Statement stmt;
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
            cmdExit = "exit",
            cmdQuery = "query",
            cmdSelectAllTeams = "sel-all-teams",
            cmdSelectAllPlayers = "sel-all-pls",
            cmdSelectPlayers = "sel-pls",
            cmdAddPlayer = "add-pl",
            cmdAddTeam = "add-team",
            cmdDeleteTeam = "del-team",
            cmdDeletePlayer = "del-pl";
        final String tblTeam = "Team", tblPlayer = "Player";


        String db = "football",
            url = "jdbc:mysql://localhost:3306/"+db+"?autoReconnect=true&useSSL=false",
            user = "le", password = "ledb";

        try (Connection con = DriverManager.getConnection(url, user, password)) {
            stmt = con.createStatement();
            out.println("[Connected]");
            initDb(stmt);
            var in = new Scanner(System.in);
            boolean isRunning = true;

            while (isRunning) {
                out.print("> ");
                var cmdArgs = in.nextLine().split("\\s+");
                if (cmdArgs.length == 0) {
                    out.println("Error: empty string");
                    continue;
                }
                String cmd = cmdArgs[0];

                try {
                    switch (cmd) {
                        case cmdQuery -> {
                            out.print("Query: ");
                            String query = in.nextLine();
                            stmt.execute(query);
                        }
                        case cmdSelectAllTeams -> {
                            printListRs(stmt.executeQuery("SELECT * FROM Team"));
                        }
                        case cmdSelectAllPlayers -> {
                            printListRs(stmt.executeQuery("SELECT * FROM Player"));
                        }
                        case cmdSelectPlayers -> {
                            if (cmdArgs.length < 2) {
                                out.printf("Error: '%s' takes 1 argument %n", cmd);
                                continue;
                            }
                            String teamName = cmdArgs[1];
                            printListRs(stmt.executeQuery(String.format(
                                "SELECT * FROM player WHERE TeamID = (SELECT ID FROM Team WHERE TeamName = '%s');",
                                teamName
                            )));
                        }
                        case cmdAddTeam -> {
                            if (cmdArgs.length < 2) {
                                out.printf("Error: '%s' takes 1 argument %n", cmd);
                                continue;
                            }
                            String teamName = cmdArgs[1];
                            if (stmt.executeQuery(String.format(
                                "SELECT 1 FROM Team WHERE TeamName = '%s';",
                                teamName
                            )).next()) {
                                out.println("Error: team '%s' already exists");
                                continue;
                            }
                            stmt.execute(String.format(
                                "INSERT INTO Team (TeamName) VALUES ('%s');",
                                teamName
                            ));
                        }
                        case cmdAddPlayer -> {
                            if (cmdArgs.length < 5) {
                                out.printf("Error: '%s' takes 4 argument %n", cmd);
                                continue;
                            }
                            String plName = cmdArgs[1], plSurname = cmdArgs[2], plPatronymic = cmdArgs[3], teamName = cmdArgs[4];
                            var rs = stmt.executeQuery(String.format(
                                "SELECT ID FROM Team WHERE TeamName = '%s'",
                                teamName
                            ));
                            String teamId;
                            if (!rs.next()) {
                                out.printf("Error: no team '%s'%n", teamName);
                                continue;
                            }
                            teamId = rs.getString(1);

                            if (exists(String.format(
                                "SELECT 1 FROM Player WHERE TeamID=%s AND PlayerName='%s' AND PlayerSurname='%s' AND PlayerPatronymic='%s';",
                                teamId, plName, plSurname, plPatronymic
                            ))) {
                                out.println("Error: player already exists");
                                continue;
                            }
                            stmt.execute(String.format(
                                "INSERT INTO Player (PlayerName, PlayerSurname, PlayerPatronymic, TeamID) VALUES ('%s', '%s', '%s', %s);",
                                plName, plSurname, plPatronymic, teamId
                            ));
                        }
                        case cmdDeleteTeam -> {
                            if (cmdArgs.length < 2) {
                                out.printf("Error: '%s' takes 1 argument %n", cmd);
                                continue;
                            }
                            String teamName = cmdArgs[1];
                            if (!exists(String.format("SELECT 1 FROM Team WHERE TeamName = '%s';", teamName))) {
                                out.printf("Error: no team '%s'%n", teamName);
                                continue;
                            }
                            stmt.execute(String.format("DELETE FROM Team WHERE TeamName = '%s';", teamName));
                        }
                        case cmdDeletePlayer -> {
                            if (cmdArgs.length < 5) {
                                out.printf("Error: '%s' takes 4 argument %n", cmd);
                                continue;
                            }
                            String plName = cmdArgs[1], plSurname = cmdArgs[2], plPatronymic = cmdArgs[3], teamName = cmdArgs[4];
                            var rs = stmt.executeQuery(String.format(
                                "SELECT ID FROM Team WHERE TeamName = '%s'",
                                teamName
                            ));
                            String teamId;
                            if (!rs.next()) {
                                out.printf("Error: no team '%s'%n", teamName);
                                continue;
                            }
                            teamId = rs.getString(1);
                            if (!exists(String.format(
                                "SELECT 1 FROM Player WHERE TeamID=%s AND PlayerName='%s' AND PlayerSurname='%s' AND PlayerPatronymic='%s';",
                                teamId, plName, plSurname, plPatronymic
                            ))) {
                                out.printf("Error: player '%s %s %s' does not exist in team '%s'", plName, plSurname, plPatronymic, teamName);
                                continue;
                            }
                            stmt.execute(String.format(
                                "DELETE FROM Player WHERE TeamID=%s AND PlayerName='%s' AND PlayerSurname='%s' AND PlayerPatronymic='%s';",
                                teamId, plName, plSurname, plPatronymic
                            ));
                        }
                        case cmdExit -> {
                            out.println("Bye");
                            isRunning = false;
                        }
                        default -> {
                            out.println("Error: unknown command");
                        }
                    }
                }
                catch (SQLException e) {
                    out.println("[SQL ERROR]");
                    out.println(e.getMessage());
                }
            }
        }
        catch (Exception e) {
            out.println("[ERROR]");
            e.printStackTrace();
        }
    }

    private static boolean exists(String sql) throws SQLException {
        return stmt.executeQuery(sql).next();
    }

    private static void printListRs(ResultSet rs) throws SQLException {
        int columnCount = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            for (int i=1; i<=columnCount; i++) {
                out.print(rs.getString(i)+" ");
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
