package lab2.p1;

import lab2.sql.MyQuery;
import lab2.sql.SQL;
import org.apache.derby.jdbc.EmbeddedDriver;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.System.out;

public class Lab2Cmd {
	public static void main(String[] args) throws ClassNotFoundException, SQLException, InterruptedException {
		boolean isRunning = true;
		final String cmdExit = "exit",
			cmdBb = "bb",
			cmdInitDb = "init",
			cmdSQL = "sql",
			cmdSelectAllTeams = "sel-all-teams",
			cmdSelectAllPlayers = "sel-all-pls",
			cmdSelectPlayersWhereTeam = "sel-pls-of",
			cmdAddTeam = "add-team",
			cmdAddPlayer = "add-pl",
			cmdDeleteTeam = "del-team",
			cmdDeletePlayer = "del-pl";

		// main loop
		while (isRunning) {
			// set db sys
			DBType dbType;
			var in = new Scanner(System.in);
			DriverManager.registerDriver(new EmbeddedDriver());
			out.println("Choose DB system (MySQL=1, SQLServer=2, Derby=3):");
			out.print(">> ");
			var inp = in.nextLine();
			switch (inp) {
				case "1" -> dbType = DBType.MySQL;
				case "2" -> dbType = DBType.SQLServer;
				case "3" -> dbType = DBType.Derby;
				case cmdExit -> {
					isRunning = false;
					continue;
				}
				default -> {
					out.println("[Error]: bad input");
					continue;
				}
			}

			try {
				SQL sql = new SQL(dbType, "football", "le", "ledb");
				// session loop
				boolean isConnectionOn = true;
				while (isConnectionOn) {
					out.print("lab2> ");
					var cmdArgs = in.nextLine().split("\\s+");
					if (cmdArgs.length == 0) {
						continue;
					}
					String cmd = cmdArgs[0];
					try {
						switch (cmd) {
							case cmdInitDb -> {
								sql.initDb();
							}
							case cmdSQL -> {
								out.print(">> ");
								var query = in.nextLine();
								try {
									out.println(sql.executeQuery(query));
								}
								catch (SQLException e) {
									try {
										sql.execute(query);
									}
									catch (SQLException e2) {
										throw e;
									}
								}
							}
							case cmdSelectAllTeams -> out.println(sql.getTableViewString(MyQuery.selectFromTeam));
							case cmdSelectAllPlayers -> out.println(sql.getTableViewString(MyQuery.selectFromPlayer));
							case cmdSelectPlayersWhereTeam -> {
								if (cmdArgs.length < 2) {
									out.println("[Cmd Error]: 1 arg required");
									continue;
								}
								// check team
								var teamName = cmdArgs[1];
								//if (lab2.sql.exists("Team", String.format("TeamName='%s'", teamName))) {
								if (sql.exists("Team", "Name='%s'", teamName)) {
									out.println(sql.getTableViewString(String.format(
										MyQuery.selectFromPlayerWhereTeamId,
										teamName)));
								}
								else out.println("[SQL Error]: this team does not exist");
							}
							case cmdAddTeam -> {
								if (cmdArgs.length < 2) {
									out.println("[Cmd Error]: 2 args required");
									continue;
								}
								String teamName = cmdArgs[1];
								if (sql.exists("Team", "Name='%s'", teamName)) {
									out.println("[SQL Error]: team already exists");
									continue;
								}
								sql.execute(MyQuery.insertIntoTeam, teamName);
							}
							case cmdAddPlayer -> {
								if (cmdArgs.length < 5) {
									out.println("[Cmd Error]: 4 args required");
									continue;
								}
								String pSrn = cmdArgs[1], pName = cmdArgs[2], pPtr = cmdArgs[3], teamName = cmdArgs[4];
								var rs = sql.executeQuery(MyQuery.selectIdFromTeamWhereName, teamName);
								int teamId;
								// check team
								if (rs.next()) {
									teamId = rs.getInt(1);
									// check player
									if (!sql.exists(MyQuery.selectFromPlayerWhere, pSrn, pName, pPtr, teamId)) {
										sql.execute(MyQuery.insertIntoPlayer, pSrn, pName, pPtr, teamId);
									}
									else out.println("[SQL Error]: player already exists");
								}
								else out.println("[SQL Error]: team does not exists");
							}
							case cmdDeleteTeam -> {
								if (cmdArgs.length < 2) {
									out.println("[Cmd Error]: 1 arg required");
									continue;
								}
								String teamName = cmdArgs[1];
								// check team
								if (sql.exists(MyQuery.selectIdFromTeamWhereName, teamName)) {
									// check players
									if (!sql.exists(MyQuery.selectFromPlayerWhereTeamId, teamName)) {
										sql.execute(MyQuery.deleteFromTeamWhereName, teamName);
									}
									else out.println("[SQL Error]: cannot delete team: some players link to it");
								}
								else out.println("[SQL Error]: team does not exists");
							}
							case cmdDeletePlayer -> {
								if (cmdArgs.length < 5) {
									out.println("Cmd Error: 4 args required");
									continue;
								}
								String pSrn = cmdArgs[1], pName = cmdArgs[2], pPtr = cmdArgs[3], teamName = cmdArgs[4];
								var rs = sql.executeQuery(MyQuery.selectIdFromTeamWhereName, teamName);
								int teamId;
								// check team
								if (rs.next()) {
									teamId = rs.getInt(1);
									// check player
									if (sql.exists(MyQuery.selectFromPlayerWhere, pSrn, pName, pPtr, teamId)) {
										sql.execute(MyQuery.deleteFromPlayerWhere, pSrn, pName, pPtr, teamId);
									}
									else out.println("[SQL Error]: player does not exists");
								}
								else out.println("[SQL Error]: team does not exists");
							}
							case cmdExit -> isConnectionOn = false;
							case cmdBb -> {
								isConnectionOn = false;
								isRunning = false;
							}
							default -> out.println("[Cmd Error]: unknown command");
						}
					}
					catch (SQLException e) {
						out.println("[SQL Error]");
						e.printStackTrace(out);
					}
					catch (Exception e) {
						out.println("[Error]");
						e.printStackTrace(out);
					}
				}
				out.println("[Connection closed]");
			}
			catch (SQLException e) {
				out.println("[SQL Error]");
				e.printStackTrace(out);
			}
		}
		out.println("Bye");
	}
}
