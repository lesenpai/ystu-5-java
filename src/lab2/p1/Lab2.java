package lab2.p1;

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
		 *
		 */
		final String cmdHelp = "help",
			cmdExit = "exit",
			cmdSQL = "sql",
			cmdSelectAllTeams = "sel-all-teams",
			cmdSelectAllPlayers = "sel-all-pls",
			cmdFilterPlayers = "sel-pls",
			cmdAddTeam = "add-team",
			cmdAddPlayer = "add-pl",
			cmdDeleteTeam = "del-team",
			cmdDeletePlayer = "del-pl";

		String url = "jdbc:mysql://localhost:3306/football?autoReconnect=true&useSSL=false",
			user = "le", password = "ledb";

		try (Connection con = DriverManager.getConnection(url, user, password)) {
			stmt = con.createStatement();
			out.println("[Connected to MySQL]");
			var in = new Scanner(System.in);
			boolean isRunning = true;

			while (isRunning) {
				out.print("lab2> ");
				var cmdArgs = in.nextLine().split("\\s+");
				if (cmdArgs.length == 0) {
					continue;
				}
				String cmd = cmdArgs[0];

				try {
					switch (cmd) {
						case cmdHelp -> {
							out.println(
								cmdExit + ": exit\n"
									+ cmdSQL + ": execute SQL query\n"
									+ cmdSelectAllTeams + ": show table Team\n"
									+ cmdFilterPlayers + ": show table Player\n"
									+ cmdFilterPlayers + ": show table Player, filtered by TeamName\n"
									+ cmdAddTeam + ": add Team\n"
									+ cmdAddPlayer + ": add Player\n"
									+ cmdDeleteTeam + ": delete Team\n"
									+ cmdDeletePlayer + ": delete Player\n"
							);
						}
						case cmdSQL -> {
							out.print("SQL: ");
							String query = in.nextLine();
							printListRs(stmt.executeQuery(query));
						}
						case cmdSelectAllTeams -> {
							printListRs(stmt.executeQuery("SELECT * FROM Team"));
						}
						case cmdSelectAllPlayers -> {
							printListRs(stmt.executeQuery("SELECT * FROM Player"));
						}
						case cmdFilterPlayers -> {
							if (cmdArgs.length < 2) {
								out.println("Cmd Error: 1 arg required");
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
								out.println("Cmd Error: 2 args required");
								continue;
							}
							String teamName = cmdArgs[1];
							if (stmt.executeQuery(String.format(
								"SELECT 1 FROM Team WHERE TeamName = '%s';",
								teamName
							)).next()) {
								out.println("Error: team already exists");
								continue;
							}
							stmt.execute(String.format(
								"INSERT INTO Team (TeamName) VALUES ('%s');",
								teamName
							));
						}
						case cmdAddPlayer -> {
							if (cmdArgs.length < 5) {
								out.println("Cmd Error: 4 args required");
								continue;
							}
							String pName1 = cmdArgs[1], pName2 = cmdArgs[2], pName3 = cmdArgs[3], teamName = cmdArgs[4];
							var rs = stmt.executeQuery(String.format(
								"SELECT ID FROM Team WHERE TeamName = '%s'",
								teamName
							));
							String teamId;
							if (!rs.next()) {
								out.println("Error: team does not exists");
								continue;
							}
							teamId = rs.getString(1);

							if (exists(String.format(
								"SELECT 1 FROM Player WHERE TeamID=%s AND PlayerName1='%s' AND PlayerName2='%s' AND PlayerName3='%s';",
								teamId, pName1, pName2, pName3
							))) {
								out.println("Error: player already exists");
								continue;
							}
							stmt.execute(String.format(
								"INSERT INTO Player (PlayerName1, PlayerName2, PlayerName3, TeamID) VALUES ('%s', '%s', '%s', %s);",
								pName1, pName2, pName3, teamId
							));
						}
						case cmdDeleteTeam -> {
							if (cmdArgs.length < 2) {
								out.println("Cmd Error: 1 arg required");
								continue;
							}
							String teamName = cmdArgs[1];
							if (!exists(String.format("SELECT 1 FROM Team WHERE TeamName = '%s';", teamName))) {
								out.println("Error: team does not exists");
								continue;
							}
							stmt.execute(String.format("DELETE FROM Team WHERE TeamName = '%s';", teamName));
						}
						case cmdDeletePlayer -> {
							if (cmdArgs.length < 5) {
								out.println("Cmd Error: 4 args required");
								continue;
							}
							String pName1 = cmdArgs[1], pName2 = cmdArgs[2], pName3 = cmdArgs[3], teamName = cmdArgs[4];
							var rs = stmt.executeQuery(String.format(
								"SELECT ID FROM Team WHERE TeamName = '%s'",
								teamName
							));
							String teamId;
							if (!rs.next()) {
								out.println("Error: team does not exists");
								continue;
							}
							teamId = rs.getString(1);
							if (!exists(String.format(
								"SELECT 1 FROM Player WHERE TeamID=%s AND PlayerName1='%s' AND PlayerName2='%s' AND PlayerName3='%s';",
								teamId, pName1, pName2, pName3
							))) {
								out.println("Cmd Error: player does not exists on this team");
								continue;
							}
							stmt.execute(String.format(
								"DELETE FROM Player WHERE TeamID=%s AND PlayerName1='%s' AND PlayerName2='%s' AND PlayerName3='%s';",
								teamId, pName1, pName2, pName3
							));
						}
						case cmdExit -> {
							out.println("Bye");
							isRunning = false;
						}
						default -> {
							out.println("Cmd Error: unknown command");
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
			for (int i = 1; i <= columnCount; i++) {
				out.print(rs.getString(i) + " ");
			}
			out.println();
		}
	}
}
